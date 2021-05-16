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

import org.apache.commons.codec.binary.Hex;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.osskey.vo.TimeBasedVO;
import org.maxkey.password.onetimepwd.algorithm.KeyUriFormat;
import org.maxkey.password.onetimepwd.algorithm.OtpSecret;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.RQCodeUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.image.ImageEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.image.BufferedImage;
import java.util.UUID;


/**
 * .
 *
 * @author hugoDD
 */
@Controller
@RequestMapping(value = {"/auth/manage/safe/otp"})
public class OtpManagerController {
    static final Logger _logger = LoggerFactory.getLogger(OtpManagerController.class);
    @Autowired
    @Qualifier("keyUriFormat")
    KeyUriFormat keyUriFormat;
    @Autowired
    @Qualifier("passwordReciprocal")
    PasswordReciprocal passwordReciprocal;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @RequestMapping(value = {"/timebased"})
    public ResponseResult<TimeBasedVO> timeBased() {
        UserInfo userInfo = genTimeBased();
        TimeBasedVO vo = init(userInfo);
        return ResponseResult.newInstance(vo);
    }


    @RequestMapping(value = {"/counterbased", "/hotp"})
    public ResponseResult<TimeBasedVO> counterBased() {
        UserInfo userInfo = genCounterBased();
        TimeBasedVO vo = init(userInfo);
        return ResponseResult.newInstance(vo);

    }

    public UserInfo genTimeBased() {
        UserInfo userInfo = initBased();
        userInfoService.changeSharedSecret(userInfo);
        return userInfo;
    }

    public UserInfo genCounterBased() {
        UserInfo userInfo = initBased();
        userInfo.setSharedCounter("0");
        userInfoService.changeSharedSecret(userInfo);
        return userInfo;
    }

    private UserInfo initBased() {
        UserInfo userInfo = WebContext.getUserInfo();
        byte[] byteSharedSecret = OtpSecret.generate(keyUriFormat.getCrypto());
        String sharedSecret = Base32Utils.encode(byteSharedSecret);
        sharedSecret = passwordReciprocal.encode(sharedSecret);
        userInfo.setSharedSecret(sharedSecret);
        return userInfo;
    }


    private TimeBasedVO init(UserInfo userInfo) {
        String sharedSecret = passwordReciprocal.decoder(userInfo.getSharedSecret());
        keyUriFormat.setSecret(sharedSecret);
        if ("0".equals(userInfo.getSharedCounter())) {
            keyUriFormat.setCounter(Long.parseLong(userInfo.getSharedCounter()));
        }
        String otpauth = keyUriFormat.format(userInfo.getUsername());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);

        TimeBasedVO vo = new TimeBasedVO();
        vo.setId(genRqCode(otpauth));
        vo.setUserInfo(userInfo);
        vo.setFormat(keyUriFormat);
        vo.setSharedSecret(sharedSecret);
        vo.setHexSharedSecret(hexSharedSecret);
        return vo;
    }


    public String genRqCode(String otpauth) {
        BufferedImage bufferedImage = RQCodeUtils.write2BufferedImage(otpauth, "gif", 300, 300);
        byte[] imageByte = ImageEndpoint.bufferedImage2Byte(bufferedImage);
        String uuid = UUID.randomUUID().toString().toLowerCase();
        WebContext.getSession().setAttribute(uuid, imageByte);
        return uuid;
    }
}
