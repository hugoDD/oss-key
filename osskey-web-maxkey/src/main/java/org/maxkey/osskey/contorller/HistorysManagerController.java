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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.maxkey.domain.HistoryLogin;
import org.maxkey.domain.HistoryLoginApps;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.domain.PageResults;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.domain.result.PageResult;
import org.maxkey.persistence.service.HistoryLoginAppsService;
import org.maxkey.persistence.service.HistoryLoginService;
import org.maxkey.persistence.service.HistoryLogsService;
import org.maxkey.rsql.RsqlToMybatisPlusWrapper;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登录日志和操作日志查询.
 *
 * @author hugoDD
 *
 */

@RestController
@RequestMapping(value = { "/auth/manage/historys" })
public class HistorysManagerController {
    static final Logger _logger = LoggerFactory.getLogger(HistorysManagerController.class);

    @Autowired
    HistoryLoginService historyLoginService;

    @Autowired
    protected HistoryLoginAppsService historyLoginAppsService;

    @Autowired
    HistoryLogsService historyLogsService;


    /**
     * 查询操作日志.
     *
     * @param search
     * @return
     */
    @PostMapping(value = { "/opt/logs" })
    public PageResult<HistoryLogs> historyLogs(@RequestBody  PageSearchFilter search) {

        IPage<HistoryLogs> page =historyLogsService.page(search.newPage(),search.rqslToQuery(HistoryLogs.class));

        return PageResult.newInstance(page.getTotal(),page.getRecords());
    }

    /**
     * 查询登录日志.
     *
     * @param search
     * @return
     */
    @PostMapping(value = { "/login/log" })
    public PageResult<HistoryLogin> LogigLogPage(@RequestBody PageSearchFilter search) {
       IPage<HistoryLogin> page =  historyLoginService.page(search.newPage(),search.rqslToQuery(HistoryLogin.class));
        return PageResult.newInstance(page.getTotal(),page.getRecords());
    }


    /**
     * 查询单点登录日志.
     *
     * @param search
     * @return
     */
    @PostMapping(value = { "/appAccess/log" })
    public PageResult<HistoryLoginApps> loginAppsLog(@RequestBody PageSearchFilter search) {
       IPage<HistoryLoginApps> page =  historyLoginAppsService.page(search.newPage(),search.rqslToQuery(HistoryLoginApps.class));

        return PageResult.newInstance(page.getTotal(),page.getRecords());
    }

}
