package com.nadou.provider.rabbitmq.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName ClientCertificateVo
 *@Description TODO
 *@Author
 *@Date 2020/6/13 07:55
 *@Version 1.0
 **/
@Data
public class ClientCertificateVo implements Serializable {
  private static final long serialVersionUID = 2615388036504010870L;

  private String ta;//客户端票据
  private String tb;//服务端票据
}
