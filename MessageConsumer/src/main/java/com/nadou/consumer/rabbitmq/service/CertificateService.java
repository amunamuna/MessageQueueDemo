package com.nadou.consumer.rabbitmq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nadou.common.utils.CommonResult;
import com.nadou.consumer.rabbitmq.vo.CerTicketVo;

@FeignClient("CERTIFICATION")
public interface CertificateService {

  @PostMapping("/cer/serverCert")
  CommonResult serverCert(@RequestBody CerTicketVo cerTicketVo);
}
