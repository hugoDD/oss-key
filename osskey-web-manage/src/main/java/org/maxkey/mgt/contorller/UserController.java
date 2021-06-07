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


package org.maxkey.mgt.contorller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.maxkey.aspectj.annotation.Log;
import org.maxkey.constants.ConstantsCode;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.ExcelImport;
import org.maxkey.domain.PageResults;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author hugoDD
 */
@RestController
@RequestMapping({"/auth/manage/user"})
public class UserController {
    final static Logger _logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;


    /**
     * 查询用户列表
     *
     * @param page
     * @return
     */
    @PostMapping(value = {"/page"})
    public PageResults<UserInfo> usersList(PageSearchFilter page) {
        //return userInfoService.queryPageResults(userInfo);
        IPage<UserInfo> rs = userInfoService.page(page.newPage());
        return new PageResults<>(rs);

    }


    /**
     * 新增
     *
     * @param userInfo
     * @return
     */
    @Log(title = "user:insert", operateType = OperateType.add, messageScope = MessageScope.DB)
    @PostMapping(value = "/add")
    public ResponseResult<String> addUsers(@Valid @RequestBody UserInfo userInfo) {
        _logger.debug(userInfo.toString());

        if (userInfoService.insert(userInfo)) {
            return ResponseResult.newInstance(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS));
        }

        String errorMessage = WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR);
        return ResponseResult.newInstance(ConstantsCode.update_fail, errorMessage);
    }


    /**
     * 查询用户，根据id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getUsers/{id}")
    public ResponseResult<UserInfo> getUserInfo(@PathVariable("id") String id) {
        _logger.debug(id);
        UserInfo userInfo = userInfoService.getById(id);
        if (userInfo != null && userInfo.getDecipherable() != null) {

            userInfo.setPassword(ReciprocalUtils.decoder(userInfo.getDecipherable()));

            userInfo.setDecipherable(userInfo.getPassword());
        }
        return ResponseResult.newInstance(userInfo);
    }




    /**
     * 修改用户
     *
     * @param userInfo
     * @return
     */
    @Log(title = "user:update", operateType = OperateType.update, messageScope = MessageScope.DB)
    @PostMapping(value = "/update")
    public ResponseResult<String> updateUsers(@Valid @RequestBody UserInfo userInfo) {

        //userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
        //userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
        convertExtraAttribute(userInfo);
        _logger.info(userInfo.getExtraAttribute());
        if (userInfoService.update(userInfo)) {
            return ResponseResult.newI18nInstance(ConstantsOperateMessage.UPDATE_SUCCESS);

        }
        return ResponseResult.newI18nInstance(ConstantsCode.update_fail,ConstantsOperateMessage.UPDATE_ERROR);

    }


    /**
     * 批量删除用户
     *
     * @param id
     * @return
     */
    @Log(title = "user:delete", operateType = OperateType.delete, messageScope = MessageScope.DB)
    @DeleteMapping(value = "/batchDelete/{+id}")
    public ResponseResult<String> batchDeleteUsers(@PathVariable("id") String[] id) {
        if (userInfoService.removeByIds(Arrays.asList(id))) {
            return ResponseResult.newI18nInstance(ConstantsOperateMessage.DELETE_SUCCESS);

        } else {
            return ResponseResult.newI18nInstance(ConstantsCode.update_fail,ConstantsOperateMessage.DELETE_ERROR);
        }
    }



    private void convertExtraAttribute(UserInfo userInfo) {
        if (userInfo.getExtraAttributeValue() != null) {
            String[] extraAttributeLabel = userInfo.getExtraAttributeName().split(",");
            String[] extraAttributeValue = userInfo.getExtraAttributeValue().split(",");
            Map<String, String> extraAttributeMap = new HashMap<String, String>();
            for (int i = 0; i < extraAttributeLabel.length; i++) {
                extraAttributeMap.put(extraAttributeLabel[i], extraAttributeValue[i]);
            }
            String extraAttribute = JsonUtils.object2Json(extraAttributeMap);
            userInfo.setExtraAttribute(extraAttribute);
        }
    }



    @Log(title = "user:changePassword", operateType = OperateType.update, messageScope = MessageScope.DB)
    @PostMapping(value = "/changePassword")
    public ResponseResult<String> changePassword(@ModelAttribute("userInfo") UserInfo userInfo) {
        _logger.debug(userInfo.getId());
        if (userInfoService.changePassword(userInfo)) {
            return ResponseResult.newI18nInstance(ConstantsOperateMessage.UPDATE_SUCCESS);

        } else {
            return ResponseResult.newI18nInstance(ConstantsCode.update_fail,ConstantsOperateMessage.UPDATE_ERROR);
        }
    }

    @RequestMapping(value = "/import",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ResponseResult<String> importing( ExcelImport excelImportFile) {
        ModelAndView modelAndView = new ModelAndView("/userinfo/usersImport");

        if (excelImportFile.getExcelFile() != null && !excelImportFile.getExcelFile().isEmpty() && userInfoService.importing(excelImportFile.getExcelFile())) {
            return ResponseResult.newI18nInstance(ConstantsOperateMessage.INSERT_SUCCESS);
        } else {
            return ResponseResult.newI18nInstance(ConstantsCode.update_fail,ConstantsOperateMessage.INSERT_ERROR);
        }

    }


}
