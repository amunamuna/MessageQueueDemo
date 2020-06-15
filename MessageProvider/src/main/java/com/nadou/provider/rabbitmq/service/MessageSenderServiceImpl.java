package com.nadou.provider.rabbitmq.service;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nadou.common.key.EncodeData;
import com.nadou.common.key.TransportDataUtils;
import com.nadou.common.key.aes.AESCipher;
import com.nadou.common.utils.CommonResult;
import com.nadou.common.utils.LogConsole;
import com.nadou.common.utils.QueueEnum;
import com.nadou.common.utils.ResultCode;
import com.nadou.provider.rabbitmq.vo.ClientCerRequest;
import com.nadou.provider.rabbitmq.vo.ClientCertificateVo;
import com.nadou.provider.rabbitmq.vo.CerTicketVo;
import com.nadou.provider.rabbitmq.vo.TicketData;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName MessageSender
 *@Description TODO
 *@Author
 *@Date 2020/1/10 3:17 PM
 *@Version 1.0
 **/
@Slf4j
@Service
public class MessageSenderServiceImpl implements MessageSenderService{

  @Autowired
  private AmqpTemplate amqpTemplate;
  @Value("${ka.password}")
  private String ka;

  @Autowired
  private CertificateService certificateService;

  @Override
  public CommonResult sendMessage(String from ,String to,String message) {
    LogConsole.info("provider向AS认证服务器发起认证");
    ClientCerRequest clientCerRequest = new ClientCerRequest(from, to);
    CommonResult commonResult = certificateService.clientCert(clientCerRequest);
    if(commonResult == null){
      LogConsole.info("认证出错");
      return CommonResult.failed("认证出错,请查询");
    }else if(ResultCode.SUCCESS.getCode() == commonResult.getCode()){
      ClientCertificateVo vo = BeanUtil
          .mapToBeanIgnoreCase((HashMap) commonResult.getData(), ClientCertificateVo.class, true);
      if(vo != null){
        LogConsole.info("provider认证成功,获取ta:"+vo.getTa()+",开始用ka解密");
        String ticket = AESCipher.decrypt(vo.getTa(), ka);
        if(ticket == null){
          LogConsole.info("解析ta出错,请核对ka"+ka+"是否与认证服务器ka保持一致!");
          return CommonResult.failed("解析ta出错");
        }
        CerTicketVo cerTicketVo = JSONObject.parseObject(ticket, CerTicketVo.class);
        if(cerTicketVo != null){
          LogConsole.info("provider通过ka将ta成功解密,开始封装队列消息");
          TicketData.getInstance().getCerTicketVoConcurrentMap().put(cerTicketVo.getKab(),cerTicketVo.getToken());
          EncodeData encodeData = TransportDataUtils.encode(message,cerTicketVo.getKab(),vo.getTb());
          //给队列发送消息
          amqpTemplate.convertAndSend(QueueEnum.QUEUE_TOPIC_DEMO.getExchange(), "haha.topic", JSON.toJSONString(encodeData));
          log.info("provider向mq发送消息:"+JSON.toJSONString(encodeData));
          return CommonResult.success(null);
        }
      }
    }
    return commonResult;

  }

  //用于AS认证中心与客户端双向认证
  @Override
  public boolean twowayAuthentication(String kab){
    if(MapUtil.isEmpty(TicketData.getInstance().getCerTicketVoConcurrentMap())){
      LogConsole.info("session key 不存在.");
      return false;
    }
    String data = TicketData.getInstance().getCerTicketVoConcurrentMap().get(kab);
    if(StringUtils.isEmpty(data)){
      LogConsole.info("session key 不存在.");
      return false;
    }
    LogConsole.info("双向认证成功.");
    return true;
  }

//  public void sendMessage(String message,final long delayTimes){
//    //给队列发送消息
//    log.info("send message:"+message);
//    amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDINARY.getExchange(), QueueEnum.QUEUE_ORDINARY.getRouteKey(), message, new MessagePostProcessor() {
//      @Override
//      public Message postProcessMessage(Message message) throws AmqpException {
//        //给消息设置延迟毫秒值
//        message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
//        return message;
//      }
//    });
//  }
}
