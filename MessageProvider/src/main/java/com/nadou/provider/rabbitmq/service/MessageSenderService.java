package com.nadou.provider.rabbitmq.service;

import com.nadou.common.utils.CommonResult;

public interface MessageSenderService {
  CommonResult sendMessage(String from ,String to,String message);

  boolean twowayAuthentication(String kab);
}
