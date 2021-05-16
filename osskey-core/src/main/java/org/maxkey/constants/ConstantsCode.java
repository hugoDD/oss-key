package org.maxkey.constants;

public interface ConstantsCode {
    int success_code = 20000; //: success
    int invalid_access_code = 50001;// invalid access token
    int already_login_code = 50002;// already login in other place
    int access_token_expired_code = 50003;// access token expired
    int invalid_user_code = 50004;// invalid user (user not exist)
    int incorrect_code = 50005;// username or password is incorrect

    int update_fail = 50200; //更新失败

    //验证码为空
    int invalid_captcha = 50210 ;
}
