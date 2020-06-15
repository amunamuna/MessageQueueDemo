package com.nadou.common.key;

import java.io.Serializable;

import lombok.Data;

/**
 *@ClassName KeyUtil
 *@Description 需推到mq的数据
 *@Author
 *@Date 2020/6/2 17:28
 *@Version 1.0
 **/
@Data
public class EncodeData implements Serializable{
  private static final long serialVersionUID = -1102157885841921709L;

  private String encryptText;//经过aes加密后的数据
  private byte[] encryptKey;//加密后的aes密钥
  private String tb;//票据

  public EncodeData(String encryptText, byte[] encryptKey,String tb) {
    this.encryptText = encryptText;
    this.encryptKey = encryptKey;
    this.tb = tb;
  }

  public EncodeData() {
  }
}
