package com.nadou.provider.rabbitmq.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName clientCerRequest
 *@Description TODO
 *@Author
 *@Date 2020/6/13 08:53
 *@Version 1.0
 **/
@Data
public class ClientCerRequest implements Serializable {
  private static final long serialVersionUID = 1246659843302988684L;
  private String from;
  private String to;

  public ClientCerRequest(String from, String to) {
    this.from = from;
    this.to = to;
  }

  public ClientCerRequest() {
  }
}
