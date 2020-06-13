package com.nadou.certificate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nadou.certificate.web.model.vo.ClientCerRequest;
import com.nadou.certificate.web.model.vo.TicketVo;
import com.nadou.common.utils.CommonResult;
import com.nadou.certificate.web.service.CertificateService;

/**
 *@ClassName CertificateController
 *@Description 认证
 *@Author nannan.zhang
 *@Date 2020/6/12 11:13
 *@Version 1.0
 **/
@Controller
@RequestMapping("/cer")
@ResponseBody
public class CertificateController {

  @Autowired
  private CertificateService certificateService;

  @PostMapping("/clientCert")
  public CommonResult clientCertificate(@RequestBody ClientCerRequest clientCerRequest ){
    return certificateService.clientCertificate(clientCerRequest.getFrom(),clientCerRequest.getTo());
  }
  @PostMapping("/serverCert")
  public CommonResult serverCertificate(@RequestBody TicketVo ticketVo){
    return certificateService.serverCertificate(ticketVo.getKab(),ticketVo.getToken());
  }
}
