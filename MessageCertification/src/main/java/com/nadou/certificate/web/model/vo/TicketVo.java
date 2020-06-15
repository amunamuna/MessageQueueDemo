package com.nadou.certificate.web.model.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName TicketVo
 *@Description 票据
 *@Author
 *@Date 2020/6/13 08:02
 *@Version 1.0
 **/
@Data
public class TicketVo implements Serializable {

  private static final long serialVersionUID = 2830766896276308637L;

  private String kab;
  private String token;

  public TicketVo(String kab, String token) {
    this.kab = kab;
    this.token = token;
  }

  public TicketVo() {
  }
}
