package com.nadou.certificate.web.model.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName ClientCerRequest
 *@Description 客户端请求认证数据
 *@Author nannan.zhang
 *@Date 2020/6/13 08:50
 *@Version 1.0
 **/
@Data
public class ClientCerRequest implements Serializable{
  private String from ;
  private String to;
}
