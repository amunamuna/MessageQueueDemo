# 概述
消息中间件:RabbitMQ

加密: AES+RSA混合加密

## MessageRegistry 注册中心

## MessageCertification 认证中心

## MessageProvider 发送方-客户端

## MessageConsumer 接收方-服务器

## MessageCommon 公共类
aes 对称加密

rsa 非对称加密,一般用公钥加密,用私钥解密






![系统架构](https://github.com/amunamuna/MessageQueueDemo/blob/master/1.png)

![系统逻辑结构](https://github.com/amunamuna/MessageQueueDemo/blob/master/2.png)

（1）	客户alice发起向客户bob推送数据的请求。
（2）	provider服务接收到请求后，将alice和bob封装成一个对象，向AS服务器发起认证请求，验证用户是否真实。
（3）	AS认证中心收到请求后，查询数据库进行判断用户是否存在，存在，即：
a)	使用SecureRandom随机产生一个16位的随机串作为两用户之间的会话密钥session key（之后统称为kab）；
b)	使用jwt技术将会话密钥kab、时间戳和校验码checksum封装，作为认证因子token。
c)	将会话密钥kab和token封装成json字符串。
d)	再根据数据库的返回结果，用两用户各自的密码使用AES对之上生成的json字符串进行加密，生成两个票据，ta和tb；
e)	将这两个票据，返回给provider服务器。
（4）	provider服务接收返回后
a)	用自身的密码ka对票据ta进行解密，解密成功后，产生两部分：kab和token，以kab为key，token为value的形式，将数据存储到ConcurrentHashMap中，便于消息消费环节的双向认证;
b)	向密钥存储的指定路径下，读取rsa加密密钥（公钥）。
（5）	provider服务获取到rsa加密密钥后，将kab作为对称密钥k，加密我们要传输的消息，生成encryptText，然后再用rsa加密k，生成encryptKey，将encryptText、encryptKey和之前的票据tb这三部分进行封装，发送到mq；
（6）	consumer服务读取到消息后，用自身的密码kb对tb进行解析；
（7）	若解析成功，将产生的会话密钥kab和token发送至认证中心；
（8）	AS认证中心，用会话密钥将认证因子token进行解析，判断校验码是否正常，时间戳是否过期,若认证成功，再向客户端provider发起双向认证，入参还是会话密钥kab和token;
（9）	provider服务从ConcurrentHashMap缓存中，读取，判断，将结果返回给认证中心；
（10）	认证中心将结果返回给consumer服务；
（11）	若返回认证结果成功，即向密钥存储的指定路径下，读取rsa解密密钥（私钥），先用rsa解密encryptKey，生成k，然后用k解密encryptText。
（12）	若成功解密成明文，将其返给客户bob。
