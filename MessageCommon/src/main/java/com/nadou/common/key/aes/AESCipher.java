package com.nadou.common.key.aes;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.nadou.common.key.rsa.Config;

/**
 *@ClassName AESCipher
 *@Description AES
 *@Author
 *@Date 2020/6/2 16:04
 *@Version 1.0
 **/


public class AESCipher {

  private static final String ENCODING = "UTF-8";

  /**
   * @Description: 加密
   * @Date:2020/6/12 21:27
   * @Parameters: [content 需要被加密的字符串, password 加密需要的密码]
   * @Return:java.lang.String
   **/
  public static String encrypt(String content, String password) throws Exception {
    Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, password);
    //根据密码器的初始化方式--加密：将数据加密
    byte[] byte_AES = cipher.doFinal(content.getBytes("utf-8"));
    //将加密后的数据转换为字符串
    return Base64.encodeBase64String(byte_AES);
  }

  /**
   * @Description: 解密
   * @Date:2020/6/12 21:34
   * @Parameters: [content 需要解密的內容, password 解密需要的密码]
   * @Return:java.lang.String
   **/
  public static String decrypt(String content, String password) {
    try {
      Cipher cipher = getCipher(Cipher.DECRYPT_MODE, password);
      //将加密并编码后的内容解码成字节数组
      byte[]result = cipher.doFinal(Base64.decodeBase64(content));
      return new String(result, ENCODING);
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @Description: 获取密码器
   * @Date:2020/6/12 21:56
   * @Parameters: [mode 1加密 2解密 , password]
   * @Return:javax.crypto.Cipher
   **/
  private static Cipher getCipher(int mode,String password) throws Exception {
    //1.构造密钥生成器，指定为AES算法,不区分大小写
    KeyGenerator keygen = KeyGenerator.getInstance("AES");
    //2.初始化密钥生成器
    //防止linux下 随机生成key
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    secureRandom.setSeed(password.getBytes(ENCODING));
    keygen.init(128, secureRandom);
    //3.产生原始对称密钥
    SecretKey original_key = keygen.generateKey();
    //4.获得原始对称密钥的字节数组
    byte[] raw = original_key.getEncoded();
    //5.根据字节数组生成AES密钥
    SecretKey key = new SecretKeySpec(raw, "AES");
    //6.根据指定算法AES自成密码器
    Cipher cipher = Cipher.getInstance("AES");
    //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
    cipher.init(mode, key);
    return cipher;
  }

  public static void main(String[] args) throws Exception {
    //客户端代码
    String text = "{\"kab\":\"Nc/你好==\",\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTkyMDExMTkzMDc1LCJleHAiOjE1OTI2MTU5OTMsImthYiI6Ik5jL1c5YmJseHBUZFN1Q0kzdFZYYWc9PSJ9.zbomGx53OlVshpBoqJgcZoEv_NgZtdOKIUxfX5kucjWkvY2vJhJlAUdINOTLCWZeq7sCp60LerKpkZqEM8AHug\"}";
    String password = "111111";
    //随机生成16位aes密钥
    String encryptText = AESCipher.encrypt(text, password);
    System.out.println("加密后:\n" + encryptText);
    String decryptText = AESCipher.decrypt(encryptText, password);
    System.out.println("解密后:\n" + decryptText);
  }
}
