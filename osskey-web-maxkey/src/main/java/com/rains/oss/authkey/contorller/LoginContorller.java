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


package com.rains.oss.authkey.contorller;

import com.rains.oss.authkey.vo.LoginConfigVO;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.domain.vo.TokenVO;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.BeanUtil;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hugoDD
 */
@RestController
@RequestMapping("/auth/osskey")
public class LoginContorller {
    private static Logger _logger = LoggerFactory.getLogger(LoginContorller.class);
    @Autowired
    @Qualifier("tfaOtpAuthn")
    protected AbstractOtpAuthn tfaOtpAuthn;
    @Autowired
    @Qualifier("applicationConfig")
    ApplicationConfig applicationConfig;
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider;
    @Autowired
    @Qualifier("socialSignOnProviderService")
    SocialSignOnProviderService socialSignOnProviderService;
    @Autowired
    @Qualifier("kerberosService")
    KerberosService kerberosService;
    @Autowired
    @Qualifier("userInfoService")
    UserInfoService userInfoService;

    /**
     * 获取当前登录配置
     * @return
     */
    @RequestMapping(value = {"/loginConfig"})
    public ResponseResult<LoginConfigVO> loginConfig() {
        _logger.debug("LoginController /login.");
        boolean isAuthenticated = WebContext.isAuthenticated();


        _logger.trace("Session Timeout MaxInactiveInterval " + WebContext.getRequest().getSession().getMaxInactiveInterval());

        //for normal login
        LoginConfigVO modelAndView = new LoginConfigVO();
        BeanUtil.copyBean(applicationConfig.getLoginConfig(), modelAndView);

        if (applicationConfig.getLoginConfig().isMfa()) {
            modelAndView.setOtpType(tfaOtpAuthn.getOtpType());
            modelAndView.setOtpInterval(tfaOtpAuthn.getInterval());
        }

        if (applicationConfig.getLoginConfig().isKerberos()) {
            modelAndView.setUserDomainUrlJson(kerberosService.buildKerberosProxys());
        }
        modelAndView.setSessionid(WebContext.getSession().getId());
        //modelAndView.addObject("jwtToken",jwtLoginService.buildLoginJwt());
        //load Social Sign On Providers
        if (applicationConfig.getLoginConfig().isSocialSignOn()) {
            _logger.debug("Load Social Sign On Providers ");
            modelAndView.setSsopList(socialSignOnProviderService.getSocialSignOnProviders());
        }

        //Object loginErrorMessage=WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
        // modelAndView.addObject("loginErrorMessage", loginErrorMessage==null?"":loginErrorMessage);
        // WebContext.removeAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
        return ResponseResult.newInstance().setData(modelAndView);
    }

    @PostMapping(value = {"/logon"})
    public ResponseResult<TokenVO> logon(
            @ModelAttribute("loginCredential") LoginCredential loginCredential) {

        TokenVO tokenVO = new TokenVO();
        Authentication authentication =  authenticationProvider.authenticate(loginCredential);
        if(authentication.isAuthenticated()){
           String token =  ((SigninPrincipal)authentication.getPrincipal()).getOnlineTicket().getTicketId();
            UserInfo userInfo = ((SigninPrincipal)authentication.getPrincipal()).getUserInfo();
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            tokenVO.setAccessToken(token);
            tokenVO.setAvatar(Base64.getEncoder().encodeToString(userInfo.getPicture()));
            tokenVO.setEmail(userInfo.getEmail());
            tokenVO.setIntroduction(userInfo.getDescription());
            tokenVO.setName(userInfo.getUsername());
            tokenVO.setPhone(userInfo.getMobile());
            tokenVO.setRoles(roles);


        }



        return ResponseResult.newInstance(tokenVO);

//        if (WebContext.isAuthenticated()) {
//            return WebContext.redirect("/forwardindex");
//        } else {
//            return WebContext.redirect("/login");
//        }

    }


    @RequestMapping("/login/{username}")
    @ResponseBody
    public HashMap<String, Object> queryLoginUserAuth(@PathVariable("username") String username) {
// 		UserInfo userInfo=new UserInfo();
// 		userInfo.setUsername(username);
        UserInfo userInfo = userInfoService.loadByUsername(username);

        HashMap<String, Object> authnType = new HashMap<String, Object>();
        authnType.put("authnType", userInfo.getAuthnType());
        authnType.put("appLoginAuthnType", userInfo.getAppLoginAuthnType());

        return authnType;
    }

    @RequestMapping("/login/otp/{username}")
    @ResponseBody
    public String produceOtp(@PathVariable("username") String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        UserInfo queryUserInfo = userInfoService.loadByUsername(username);//(userInfo);
        if (queryUserInfo != null) {
            tfaOtpAuthn.produce(queryUserInfo);
            return "ok";
        }

        return "fail";
    }

}
