package com.nadou.provider.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nadou.common.utils.CommonResult;
import com.nadou.provider.rabbitmq.service.MessageSenderService;

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName SendMessageController
 *@Description TODO
 *@Author
 *@Date 2020/1/8 3:44 PM
 *@Version 1.0
 **/

@RestController
@Slf4j
public class SendMessageController {

  @Autowired
  private MessageSenderService messageSenderService;

  @GetMapping("/sendMessage")
  public CommonResult sendDirectMessage(String from ,String to,String msg) throws Exception {
    return messageSenderService.sendMessage(from,to,msg);
  }
  @PostMapping("/twowayAuthentication")
  public CommonResult twowayAuthentication(@RequestParam("kab")String kab) throws Exception {
    if (messageSenderService.twowayAuthentication(kab)){
      return CommonResult.success(null);
    }
    return CommonResult.failed();
  }


}
