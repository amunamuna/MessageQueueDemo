package com.nadou.certificate.web.service;

import com.nadou.common.utils.CommonResult;

public interface CertificateService {

  CommonResult clientCertificate(String from, String to) ;
  CommonResult serverCertificate(String kab, String authToken);
}
