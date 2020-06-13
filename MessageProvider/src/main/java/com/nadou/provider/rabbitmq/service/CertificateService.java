package com.nadou.provider.rabbitmq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nadou.common.utils.CommonResult;
import com.nadou.provider.rabbitmq.vo.ClientCerRequest;

@FeignClient("CERTIFICATION")
public interface CertificateService {

  @PostMapping("/cer/clientCert")
  CommonResult clientCert(@RequestBody ClientCerRequest clientCerRequest);
}
