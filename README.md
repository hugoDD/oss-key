
 
什么是<b>单点登录(Single Sign On）</b>，简称为<b>SSO</b>？

用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录。
  
主要功能： 

1.所有应用系统共享一个身份认证系统

2.所有应用系统能够识别和提取ticket信息
 
 
------------

1.  标准认证协议：

| 序号    | 协议   |  支持  |
| --------| :-----  | :----:  |
| 1       | OAuth 2.0/OpenID Connect   |  高  |
| 2       | SAML 2.0   				   |  高  |
| 3       | JWT  					   |  高  |
| 4       | CAS						   |  高  |
| 5       | FormBased				   |  中  |
| 6       | TokenBased(Post/Cookie)	   |  中  |
| 7       | ExtendApi				   |  低  |
| 8       | EXT						   |  低  |

2. 登录支持

| 序号    | 登录方式   | 
| --------| :-----  |
| 1       | 动态验证码  字母/数字/算术 	| 
| 2       | 双因素认证   	| 
| 3       | 短信认证  腾讯云短信/阿里云短信/网易云信 	|
| 4       | Google/Microsoft Authenticator/FreeOTP/支持TOTP或者HOTP |
| 5       | Kerberos/SPNEGO/AD域|
| 6       | 社交账号 微信/QQ/微博/钉钉/Google/Facebook/其他  | 


3. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

4. 提供用户生命周期管理，支持SCIM 2协议，基于Apache Kafka代理，通过连接器(Connector)实现身份供给同步。

5. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

6. 多种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录。

7. 基于Java平台开发，采用Spring、MySQL、Tomcat、Apache Kafka、Redis等开源技术，支持微服务，扩展性强。  

8. 许可证 Apache License, Version 2.0，开源免费。 

