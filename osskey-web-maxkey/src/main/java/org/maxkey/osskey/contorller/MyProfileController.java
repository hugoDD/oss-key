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
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = { "/auth/manage/profile" })
public class MyProfileController {
    static final Logger _logger = LoggerFactory.getLogger(MyProfileController.class);

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = { "/myProfile" })
    public ResponseResult<UserInfo> myProfile() {

        UserInfo userInfo = userInfoService.loadByUsername(WebContext.getUserInfo().getUsername());

        return ResponseResult.newInstance(userInfo);
    }

    /**
     * 修改用户.
     *
     * @param userInfo
     * @return
     */
    @PostMapping(value = "/update/myProfile")
    @Log(title = "updateProfile",operateType = OperateType.update,messageScope = MessageScope.DB)
    public ResponseResult<UserInfo> updateProfile(
                @RequestBody UserInfo userInfo) {
        if(_logger.isDebugEnabled()){
            _logger.debug(userInfo.toString());
        }


        if (userInfoService.updateProfile(userInfo) > 0) {
           return ResponseResult.newInstance(userInfo);

        }

        return ResponseResult.newInstance(ConstantsCode.update_fail);

    }

}
