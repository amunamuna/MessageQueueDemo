package com.nadou.common.key;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import com.nadou.common.key.rsa.RSACipher;
import com.nadou.common.key.rsa.Config;
import com.nadou.common.key.aes.AESCipher;
import com.nadou.common.utils.LogConsole;
import com.nadou.common.utils.SecureRandomUtil;

/**
 *@ClassName TrasportDataUtils
 *@Description 自己封装的加/解密
 *@Author nannan.zhang
 *@Date 2020/6/2 16:05
 *@Version 1.0
 **/

public class TransportDataUtils {
  private static final String ENCODING = "UTF-8";

  /**
  * @Description: 加密
  * @Author:nannan.zhang
  * @Date:2020/6/2 17:47
  * @Parameters:
  * @Return:
  **/
  public static EncodeData encode(String content,String aesKey,String tb) {
    try {
      LogConsole.info("将session key作为aeskey对信息加密");
      //使用aes密钥对数据进行加密
      String encryptText = AESCipher.encrypt(content,aesKey);
      byte[] aesKeyBytes = aesKey.getBytes(ENCODING);
      LogConsole.info("经过aes加密后的数据:\n" + Arrays.toString(aesKeyBytes));
      //使用服务端公钥加密aes密钥
      byte[] encryptKey = RSACipher.encrypt(Config.SERVER_PUBLIC_KEY, aesKeyBytes);
      LogConsole.info("由rsa加密后的aes密钥:\n" + Arrays.toString(encryptKey));
      return new EncodeData(encryptText,encryptKey,tb);
    } catch (Exception e) {
      LogConsole.info("加密失败",e);
    }
    return null;
  }

  /**
  * @Description: 解密
  * @Author:nannan.zhang
  * @Date:2020/6/2 17:47
  * @Parameters:
  * @Return:
  **/
  public static String decode(String encryptText, byte[] encryptKey) throws Exception {
    //服务端代码
    //使用服务端私钥对加密后的aes密钥解密
    byte[] result = RSACipher.decrypt(Config.SERVER_PRIVATE_KEY, encryptKey);
    LogConsole.info("rsa解密后的aes密钥:\n" + Arrays.toString(result));
    //使用aes私钥解密密文
    String decryptText = AESCipher.decrypt(encryptText,new String(result,ENCODING) );
    LogConsole.info("经过aes解密后的数据:\n" + decryptText);
    return decryptText;
  }

    public static void main(String[] args) throws Exception {
      EncodeData encodeData = encode("你好", "S9eqy7so/OGS+dJij88Buw==",
          "app");
      decode(encodeData.getEncryptText(), encodeData.getEncryptKey());
    }

}
