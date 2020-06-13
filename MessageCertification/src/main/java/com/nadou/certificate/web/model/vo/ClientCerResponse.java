package com.nadou.certificate.web.model.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName ClientCertificateData
 *@Description 客户端认证返回数据
 *@Author nannan.zhang
 *@Date 2020/6/12 15:56
 *@Version 1.0
 **/
@Data
public class ClientCerResponse implements Serializable {
  private static final long serialVersionUID = -2125159773214116448L;

  private String ta;
  private String tb;

  public ClientCerResponse(String ta, String tb) {
    this.ta = ta;
    this.tb = tb;
  }

  public ClientCerResponse() {
  }
}
