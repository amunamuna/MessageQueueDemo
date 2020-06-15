package com.nadou.common.utils;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

/**
 *@ClassName SecureRandomUtil
 *@Description 获取随机数
 *@Author
 *@Date 2020/6/2 16:10
 *@Version 1.0
 **/
public class SecureRandomUtil {

  public static String getRandom(int num){
    SecureRandom random = new SecureRandom();
    return Base64.encodeBase64String(random.generateSeed(num));
  }

  public static void main(String[] args) {
    System.out.println(getRandom(16));
  }
}
