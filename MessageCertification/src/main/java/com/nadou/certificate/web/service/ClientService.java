package com.nadou.certificate.web.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nadou.common.utils.CommonResult;

@FeignClient("PROVIDER")
public interface ClientService {
  @PostMapping("/twowayAuthentication")
  CommonResult clientCert(@RequestParam("kab")String kab);
}
