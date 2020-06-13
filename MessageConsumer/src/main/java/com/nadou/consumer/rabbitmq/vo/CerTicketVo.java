package com.nadou.consumer.rabbitmq.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName TicketVo
 *@Description
 *@Author nannan.zhang
 *@Date 2020/6/13 08:02
 *@Version 1.0
 **/
@Data
public class CerTicketVo implements Serializable {

  private static final long serialVersionUID = 2700124169109278018L;

  private String kab;
  private String token;

  public CerTicketVo(String kab, String token) {
    this.kab = kab;
    this.token = token;
  }

  public CerTicketVo() {
  }
}
