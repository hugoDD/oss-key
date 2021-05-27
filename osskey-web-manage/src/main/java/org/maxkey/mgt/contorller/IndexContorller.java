package org.maxkey.mgt.contorller;

import lombok.extern.slf4j.Slf4j;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.service.ReportService;
import org.maxkey.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/auth/osskey/mgt")
public class IndexContorller {

    @Autowired
    @Qualifier("reportService")
    ReportService reportService;

    @GetMapping(value={"/main"})
    public ResponseResult<Map<String,Object>> home() {
        log.debug("IndexController /main.");
        Map<String,Object> mainMView=new HashMap<>();
        mainMView.put("rptDayCount", reportService.analysisDay(""));
        mainMView.put("rptNewUsers", reportService.analysisNewUsers(""));
        mainMView.put("rptOnlineUsers", reportService.analysisOnlineUsers(""));
        mainMView.put("rptActiveUsers", reportService.analysisActiveUsers(""));

        List<Map<String,Object>> activeUsersOfWeek=reportService.analysisActiveUsersOfWeek("");

        List<Map<String,Object>> rptMonth = reportService.analysisMonth("");
        LocalDateTime start = DateTimeUtil.getWeekBeginTime(false);
        LocalDateTime end = start.plusDays(7);

        List<Long> rptOfWeek = new ArrayList<>();
        List<Long> activeUsersList = new ArrayList<>();
        for(LocalDateTime sd = start; !sd.isEqual(end); sd = sd.plusDays(1)){
            Long time = DateTimeUtil.getTimestampOfDateTime(sd);
            OptionalLong rpt = rptMonth.stream().filter(m->time==((Date)m.get("REPORTSTRING")).getTime()).mapToLong(m-> (long) m.get("REPORTCOUNT")).findFirst();
            OptionalLong activeUsers = activeUsersOfWeek.stream().filter(m->time==((Date)m.get("REPORTSTRING")).getTime()).mapToLong(m-> (long) m.get("REPORTCOUNT")).findFirst();
            if(rpt.isPresent()){
                rptOfWeek.add(rpt.getAsLong());
            }else {
                rptOfWeek.add(0L);
            }
            if(activeUsers.isPresent()){
                activeUsersList.add(activeUsers.getAsLong());
            }else {
                activeUsersList.add(0L);
            }
        }
        Map<String,List<Long>> analysis = new HashMap<>();
        analysis.put("rptOfWeek",rptOfWeek);
        analysis.put("activeUsersOfWeek",activeUsersList);

        mainMView.put("rptMonth", reportService.analysisMonth(""));
        mainMView.put("analysis", analysis);
        mainMView.put("rptDayHour", reportService.analysisDayHour(""));
        mainMView.put("rptBrowser", reportService.analysisBrowser(null));
        mainMView.put("rptApp", reportService.analysisApp(null));
        return  ResponseResult.newInstance(mainMView);
    }
}
