/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.maxkey.osskey.contorller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.maxkey.constants.ConstantsCode;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.osskey.vo.ForgotPasswordVO;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping(value = { "/auth/manage/forgotpassword" })
public class PasswordManagerContorller {
    private static Logger _logger = LoggerFactory.getLogger(PasswordManagerContorller.class);

    public class ForgotType{
        public final static int NOTFOUND = 1;
        public final static int EMAIL = 2;
        public final static int MOBILE = 3;
        public final static int CAPTCHAERROR = 4;
    }

    public class PasswordResetResult{
        public final static int SUCCESS = 1;
        public final static int CAPTCHAERROR = 2;
        public final static int PASSWORDERROR = 3;
    }

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("tfaMailOtpAuthn")
    protected AbstractOtpAuthn tfaMailOtpAuthn;

    @Autowired
    @Qualifier("tfaMobileOtpAuthn")
    protected AbstractOtpAuthn tfaMobileOtpAuthn;



    @GetMapping(value = { "/emailmobile" })
    public ResponseResult<ForgotPasswordVO> email(@RequestParam String emailMobile, @RequestParam String captcha) {
        if (_logger.isDebugEnabled()) {
            _logger.debug("emailMobile : {}", emailMobile);
        }
        if (StringUtils.isNotEmpty(captcha)) {
            ResponseResult.newInstance(ConstantsCode.invalid_captcha);
        }

        int forgotType = ForgotType.NOTFOUND;
        UserInfo userInfo = null;
        if (WebContext.captchaValid(captcha)) {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            if (StringUtils.isValidEmail(emailMobile)) {
                queryWrapper.lambda().eq(UserInfo::getEmail, emailMobile);
                userInfo = userInfoService.getOne(queryWrapper);
                if (userInfo != null) {
                    tfaMailOtpAuthn.produce(userInfo);
                    forgotType = ForgotType.EMAIL;
                }

            } else if (StringUtils.isValidMobileNo(emailMobile)) {
                queryWrapper.lambda().eq(UserInfo::getMobile, emailMobile);
                userInfo = userInfoService.getOne(queryWrapper);
                if (userInfo != null) {
                    tfaMobileOtpAuthn.produce(userInfo);
                    forgotType = ForgotType.MOBILE;
                }

            } else {
                _logger.debug("login captcha valid error.");
                forgotType = ForgotType.CAPTCHAERROR;
            }
        }
        ForgotPasswordVO vo = new ForgotPasswordVO();
        vo.setUserId(userInfo == null ? "" : userInfo.getId());
        vo.setUsername(userInfo == null ? "" : userInfo.getUsername());
        vo.setEmailMobile(emailMobile);
        vo.setForgotType(forgotType);
        return ResponseResult.newInstance(vo);

    }
    @PutMapping(value = { "/updatePassword" })
    public ResponseResult<Integer> setPassWord(
                        @RequestParam String userId,
                        @RequestParam String username,
                        @RequestParam int forgotType,
                        @RequestParam String password,
                        @RequestParam String confirmpassword,
                        @RequestParam String captcha) {
        int passwordResetResult = PasswordResetResult.PASSWORDERROR;


        if (null != password && password.equals(confirmpassword)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setDecipherable(password);
            if ((forgotType == ForgotType.EMAIL && tfaMailOtpAuthn.validate(userInfo, captcha)) ||
                    (forgotType == ForgotType.MOBILE && tfaMobileOtpAuthn.validate(userInfo, captcha))
                ) {
                userInfoService.changePassword(userInfo);
                passwordResetResult = PasswordResetResult.SUCCESS;
            } else {
                passwordResetResult =  PasswordResetResult.CAPTCHAERROR;
            }
        }
        return ResponseResult.newInstance(ConstantsCode.success_code,passwordResetResult);
    }
}
