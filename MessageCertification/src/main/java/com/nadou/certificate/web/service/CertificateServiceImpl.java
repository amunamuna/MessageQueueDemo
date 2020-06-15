package com.nadou.certificate.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nadou.certificate.web.model.vo.TicketVo;
import com.nadou.common.utils.CommonResult;
import com.nadou.common.utils.LogConsole;
import com.nadou.common.utils.ResultCode;
import com.nadou.common.utils.SecureRandomUtil;
import com.nadou.certificate.web.model.UmsAdmin;
import com.nadou.certificate.utils.JwtTokenUtil;
import com.nadou.certificate.web.mapper.UmsAdminMapper;
import com.nadou.certificate.web.model.UmsAdminExample;
import com.nadou.certificate.web.model.vo.ClientCerResponse;
import com.nadou.common.key.aes.AESCipher;

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName certificateService
 *@Description 认证
 *@Author
 *@Date 2020/6/12 11:11
 *@Version 1.0
 **/
@Service
@Slf4j
public class CertificateServiceImpl implements CertificateService {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UmsAdminMapper umsAdminMapper;

  @Autowired
  private ClientService clientService;

  //发送方认证
  @Override
  public CommonResult clientCertificate(String from, String to) {
    UmsAdmin fromAdmin = getAdminByUsername(from);
    UmsAdmin toAdmin = getAdminByUsername(to);
    if (fromAdmin == null) {
      String msg = "客户端:" + from + ":认证失败,用户不存在";
      LogConsole.info(msg);
      return CommonResult.failed(msg);
    }
    if (toAdmin == null) {
      String msg = "客户端:" + to + ":认证失败,用户不存在";
      LogConsole.info(msg);
      return CommonResult.failed(msg);
    }
    LogConsole.info("客户端:" + from + "用户有效,开始生成票据");
    Object ticket = createTicket(fromAdmin, toAdmin);
    return CommonResult.success(ticket);
  }

  //生成票据
  private Object createTicket(UmsAdmin fromAdmin, UmsAdmin toAdmin) {
    String kab = SecureRandomUtil.getRandom(16);//Session Key
    LogConsole.info("生成用于" + fromAdmin.getUsername() + "和" + toAdmin.getUsername() + "通信的session key:" + kab);
    String token = jwtTokenUtil.generateToken(kab);
    LogConsole.info("通过session key生成认证因子Authenticator:" + token);
    try {
      TicketVo ticketVo = new TicketVo(kab, token);
      String ticket = JSONObject.toJSONString(ticketVo);
      LogConsole.info("session key和Authenticator组成的json串:" + ticket);
      String ta = AESCipher.encrypt(ticket, fromAdmin.getPassword());//ka
      LogConsole.info("用" + fromAdmin.getUsername() + "的密码加密session key和Authenticator,生成ta:" + ta);
      String tb = AESCipher.encrypt(ticket, toAdmin.getPassword());//kb
      LogConsole.info("用" + toAdmin.getUsername() + "的密码加密session key和Authenticator,生成tb:" + tb);
      ClientCerResponse clientCerResponse = new ClientCerResponse(ta, tb);
      LogConsole.info("将ta,tb封装成json返回给客户端provider.");
      return JSONObject.toJSON(clientCerResponse);
    } catch (Exception e) {
      log.error("ase加密失敗:", e);
    }
    return null;
  }

  //接收方认证
  @Override
  public CommonResult serverCertificate(String kab, String authToken) {
    if (jwtTokenUtil.validateToken(authToken, kab)) {
      LogConsole.info("AS与consumer单向认证成功,开始与provider进行双向认证");
      CommonResult commonResult = clientService.clientCert(kab);
      if (commonResult != null && (ResultCode.SUCCESS.getCode() == commonResult.getCode())) {
        LogConsole.info("与provider双向认证成功");
        return commonResult;
      }
      return CommonResult.failed("信息接收端,认证失败:AS与客户端双向认证失败!");
    }
    LogConsole.info("consumer,AS认证失败!");
    return CommonResult.failed("consumer,AS认证失败!");
  }

  public UmsAdmin getAdminByUsername(String username) {
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andUsernameEqualTo(username);
    List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
    if (adminList != null && adminList.size() > 0) {
      return adminList.get(0);
    }
    return null;
  }
}
