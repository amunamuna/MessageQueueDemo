package com.nadou.common.utils;

/**
 *@ClassName ValidatingCert
 *@Description TODO
 *@Author
 *@Date 2020/1/19 3:31 PM
 *@Version 1.0
 **/
public class ValidatingCert {
  // RabbitMQ服务端地址、端口、用户名、密码
  private static final String ADDRESS = "192.168.80.40";

  private static final int PORT = 5672;
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";
//  private static final String VIRTUALHOST = "demo_VH";
  private static final String VIRTUALHOST = "/";

  private static final String QUEUE_NAME = "rabbitmq-java-test";
  // 使用tls-gen工具生成证书文件时设置的私钥密码
  private static final String CLIENT_KEYSTORE_PASSWORD = "123456";
  // 客户端证书文件client_key.p12路径
  private static final String CLIENT_KEYSTORE_PATH = "/Users/zhangnannan/mydata/rabbitmq-ssl/client_key.p12";

  // 使用keytool生成证书文件时填写的密码
  private static final String SERVER_KEYSTORE_PASSWORD = "123456";

  // 使用keytool生成的服务端证书文件路径
  private static final String SERVER_KEYSTORE_PATH = "/Users/zhangnannan/mydata/rabbitmq-ssl/rabbitstore";

//  public static void main(String[] args)  throws Exception{
//    {
//      char[] keyPassphrase = CLIENT_KEYSTORE_PASSWORD.toCharArray();
//      KeyStore ks = KeyStore.getInstance("PKCS12");
//      ks.load(new FileInputStream(CLIENT_KEYSTORE_PATH), keyPassphrase);
//
//      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//      kmf.init(ks, keyPassphrase);
//
//      char[] trustPassphrase = SERVER_KEYSTORE_PASSWORD.toCharArray();
//      KeyStore tks = KeyStore.getInstance("JKS");
//      tks.load(new FileInputStream(SERVER_KEYSTORE_PATH), trustPassphrase);
//
//      TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//      tmf.init(tks);
//
//      SSLContext c = SSLContext.getInstance("TLSv1.2");
//      c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//
//      ConnectionFactory factory = new ConnectionFactory();
//      factory.setHost(ADDRESS);
//      factory.setPort(PORT);
//      factory.setUsername(USERNAME);
//      factory.setPassword(PASSWORD);
//      factory.setVirtualHost(VIRTUALHOST);
//      factory.useSslProtocol(c);
////      factory.enableHostnameVerification();
//
//      Connection conn = factory.newConnection();
//      Channel channel = conn.createChannel();
//
//      channel.queueDeclare(QUEUE_NAME, false, true, true, null);
//      channel.basicPublish("", QUEUE_NAME, null, "Hello, World".getBytes());
//
//      GetResponse chResponse = channel.basicGet(QUEUE_NAME, false);
//      if (chResponse == null) {
//        System.out.println("No message retrieved");
//      } else {
//        byte[] body = chResponse.getBody();
//        System.out.println("Received: " + new String(body));
//      }
//
//      channel.close();
//      conn.close();
//    }
//    }

}
