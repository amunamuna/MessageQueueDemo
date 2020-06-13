package com.nadou.consumer.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.nadou.common.key.TransportDataUtils;
import com.nadou.common.key.aes.AESCipher;
import com.nadou.common.utils.CommonResult;
import com.nadou.common.utils.LogConsole;
import com.nadou.common.utils.ResultCode;
import com.nadou.consumer.rabbitmq.service.CertificateService;
import com.nadou.consumer.rabbitmq.vo.CerTicketVo;

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName MessageReceiver
 *@Description 信息接收端
 *@Author nannan.zhang
 *@Date 2020/1/10 2:15 PM
 *@Version 1.0
 **/
@Component
@Slf4j
public class MessageReceiver {

  @Value("${kb.password}")
  private String kb;

  @Autowired
  private CertificateService certificateService;

  @RabbitListener(queues = "log.queue")//监听的队列名称
  public void handle(String message) {
    try {
      JSONObject jsonObject = JSONObject.parseObject(message);
      if (jsonObject == null) {
        LogConsole.info("客户端接收数据,格式有问题:" + message);
      } else {
        LogConsole.info("客户端获取的队列原始消息\n:" + message);
        String tb = jsonObject.getString("tb");
        String ticket = AESCipher.decrypt(tb, kb);
        if (ticket == null) {
          LogConsole.info("解析tb出错,请核对kb" + kb + "是否与认证服务器kb保持一致!");
        } else {
          CerTicketVo cerTicketVo = JSONObject.parseObject(ticket, CerTicketVo.class);
          LogConsole.info("通过kb成功解密tb:"+tb);
          LogConsole.info("向AS认证服务器发起认证");
          CommonResult commonResult = certificateService.serverCert(cerTicketVo);
          if (commonResult != null && (ResultCode.SUCCESS.getCode() == commonResult.getCode())) {
            LogConsole.info("AS认证成功");
            String encryptText = jsonObject.getString("encryptText");
            byte[] encryptKey = jsonObject.getBytes("encryptKey");
            try {
              String decodeData = TransportDataUtils.decode(encryptText, encryptKey);
              log.info("客户端解密后数据:" + decodeData);
            } catch (Exception e) {
              LogConsole.info("解密出錯:",e);
            }
          }else{
            LogConsole.info("AS认证失败,请查询.");
          }
        }
      }
    } catch (Exception e) {
      LogConsole.info("消费消息出錯:",e);
      // todo  解决消息队列重复试错的bug 抛出一个致命异常就会抛弃消费这个消息
      throw new MessageConversionException("消息消费失败，移出消息队列，不再试错");
    }
  }
}
