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

import org.maxkey.aspectj.annotation.Log;
import org.maxkey.constants.ConstantsCode;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/auth/manage/safe"})
public class SafeManagerController {
    final static Logger _logger = LoggerFactory.getLogger(SafeManagerController.class);

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping({"/forward/changePasswod", "/forward/changeAppLoginPasswod", "/forward/setting"})
    public ResponseResult<UserInfo> fowardChangePasswod() {
        return ResponseResult.newInstance(WebContext.getUserInfo());
    }

    @Log(title = "safeChangePassword", operateType = OperateType.update, messageScope = MessageScope.DB)
    @PutMapping("/changePassword")
    public ResponseResult<String> changePasswod(
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {

        if (userInfoService.changePassword(oldPassword, newPassword, confirmPassword)) {
            return ResponseResult.newInstance(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS));
        } else {
            String errorMessage = (String)WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT);
            return ResponseResult.newInstance(ConstantsCode.update_fail,errorMessage);
        }
    }


    @PutMapping(value = "/changeAppLoginPasswod")
    @Log(title = "changeAppLoginPasswod", operateType = OperateType.update, messageScope = MessageScope.DB)
    public ResponseResult<Message> changeAppLoginPasswod(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {

        UserInfo userInfo = WebContext.getUserInfo();
        _logger.debug("App Login Password : " + userInfo.getAppLoginPassword());
        _logger.debug("App Login new Password : " + ReciprocalUtils.encode(newPassword));
        if (newPassword.equals(confirmPassword)) {
            if (StringUtils.isNullOrBlank(userInfo.getAppLoginPassword()) || userInfo.getAppLoginPassword().equals(ReciprocalUtils.encode(oldPassword))) {
                userInfo.setAppLoginPassword(ReciprocalUtils.encode(newPassword));
                boolean change = userInfoService.changeAppLoginPassword(userInfo);
                _logger.debug("" + change);
                Message message = new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS), MessageType.prompt);
                return ResponseResult.newInstance(message);
            }
        }

        Message message = new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR), MessageType.error);
        return ResponseResult.newInstance(ConstantsCode.update_fail, message);

    }


    @PutMapping(value = "/setting")
    @Log(title = "setting", operateType = OperateType.update, messageScope = MessageScope.DB)
    public ResponseResult<Message> setting(
            HttpServletResponse response,
            @RequestParam("authnType") String authnType,
            @RequestParam("mobile") String mobile,
            @RequestParam("mobileVerify") String mobileVerify,
            @RequestParam("email") String email,
            @RequestParam("emailVerify") String emailVerify,
            @RequestParam("theme") String theme) {
        UserInfo userInfo = WebContext.getUserInfo();
        userInfo.setAuthnType(Integer.parseInt(authnType));
        userInfoService.changeAuthnType(userInfo);

        userInfo.setMobile(mobile);
        userInfoService.changeMobile(userInfo);

        userInfo.setEmail(email);

        userInfo.setTheme(theme);
        WebContext.setCookie(response, null, WebConstants.THEME_COOKIE_NAME, theme, ConstantsTimeInterval.ONE_WEEK);

        userInfoService.changeEmail(userInfo);


        Message message = new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS), MessageType.success);
        return ResponseResult.newInstance(message);

    }

}
