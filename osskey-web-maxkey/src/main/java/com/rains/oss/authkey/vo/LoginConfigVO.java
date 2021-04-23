package com.rains.oss.authkey.vo;

import lombok.Data;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;

import java.util.List;

@Data
public class LoginConfigVO {

    private boolean captcha;

    //验证码类型 text 文本 ， arithmetic算术验证码
    private String captchaType;

    private boolean mfa;

    private boolean socialSignOn;

    private boolean kerberos;

    private boolean remeberMe;

    private boolean wsFederation;

    private String defaultUri;

    String otpType;
    int otpInterval;
    String userDomainUrlJson;
    String sessionid;
    List<SocialSignOnProvider> ssopList;

}
