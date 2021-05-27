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


package org.maxkey.persistence.service;


import org.maxkey.persistence.mapper.ReportMapper;
import org.maxkey.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {


    private ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public ReportMapper getMapper() {
        return reportMapper;
    }

    public Integer analysisDay(String reportParameter) {
        return getMapper().analysisDay(reportParameter);
    }

    ;

    public Integer analysisNewUsers(String reportParameter) {
        return getMapper().analysisNewUsers(reportParameter);
    }

    ;

    public Integer analysisOnlineUsers(String reportParameter) {
        return getMapper().analysisOnlineUsers(reportParameter);
    }

    ;

    public Integer analysisActiveUsers(String reportParameter) {
        return getMapper().analysisActiveUsers(reportParameter);
    }

    ;

    public List<Map<String, Object>> analysisDayHour(String reportParameter) {
        return getMapper().analysisDayHour(reportParameter);
    }

    public List<Map<String, Object>> analysisMonth(String reportParameter) {
        String startDate = DateTimeUtil.getWeekBeginTimeString(false);
        String endDate = DateTimeUtil.getWeekEndTimeString(false);

        List<Map<String, Object>> map = getMapper().analysisMonth(startDate, endDate);


        return map;
    }

    public List<Map<String, Object>> analysisActiveUsersOfWeek(String reportParameter) {
        String startDate = DateTimeUtil.getWeekBeginTimeString(false);
        String endDate = DateTimeUtil.getWeekEndTimeString(false);

        return getMapper().analysisActiveUsersOfWeek(startDate, endDate);
    }


    public List<Map<String, Object>> analysisBrowser(Map<String, Object> reportParameter) {
        return getMapper().analysisBrowser(reportParameter);
    }

    public List<Map<String, Object>> analysisApp(Map<String, Object> reportParameter) {
        return getMapper().analysisApp(reportParameter);
    }


}
