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

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;
import org.maxkey.domain.result.PageResult;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * AppListController.
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/auth/manage/apps")
public class AppManagerController {
    static final Logger _logger = LoggerFactory.getLogger(AppManagerController.class);
    @Autowired
    AccountsService appUsersService;
    @Autowired
    AppsService appsService;
    @Autowired
    private UserInfoService userInfoService;

    public static BufferedImage byte2BufferedImage(byte[] imageByte) {
        try {
            InputStream in = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(in);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    @GetMapping(value = {"/appList"})
    public PageResult<UserApps> appList() {
        UserApps userApplications = new UserApps();
        userApplications.setUsername(WebContext.getUserInfo().getUsername());

        List<UserApps> appList = appsService.queryMyApps(userApplications);

        return PageResult.newInstance(appList);
    }





}
