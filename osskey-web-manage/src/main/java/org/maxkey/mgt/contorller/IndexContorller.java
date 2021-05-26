package org.maxkey.mgt.contorller;

import lombok.extern.slf4j.Slf4j;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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

        mainMView.put("rptMonth", reportService.analysisMonth(""));
        mainMView.put("rptDayHour", reportService.analysisDayHour(""));
        mainMView.put("rptBrowser", reportService.analysisBrowser(null));
        mainMView.put("rptApp", reportService.analysisApp(null));
        return  ResponseResult.newInstance(mainMView);
    }
}
