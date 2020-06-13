package com.nadou.provider.rabbitmq.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName TicketVo
 *@Description TODO
 *@Author nannan.zhang
 *@Date 2020/6/13 08:02
 *@Version 1.0
 **/
@Data
public class CerTicketVo implements Serializable {
  private static final long serialVersionUID = 4525982466095201337L;

  private String kab;
  private String token;

  public CerTicketVo(String kab, String token) {
    this.kab = kab;
    this.token = token;
  }

  public CerTicketVo() {
  }
}
