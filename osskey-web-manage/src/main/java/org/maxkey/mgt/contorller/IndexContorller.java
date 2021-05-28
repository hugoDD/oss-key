package org.maxkey.mgt.contorller;

import lombok.extern.slf4j.Slf4j;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.mgt.vo.BarChartVO;
import org.maxkey.mgt.vo.PieChartVO;
import org.maxkey.mgt.vo.PieData;
import org.maxkey.persistence.service.ReportService;
import org.maxkey.util.DateTimeUtil;
import org.maxkey.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth/osskey/mgt")
public class IndexContorller {

    @Autowired
    @Qualifier("reportService")
    ReportService reportService;

    @GetMapping(value = {"/main"})
    public ResponseResult<Map<String, Object>> home() {
        log.debug("IndexController /main.");
        Map<String, Object> mainMView = new HashMap<>();
        mainMView.put("rptDayCount", reportService.analysisDay(""));
        mainMView.put("rptNewUsers", reportService.analysisNewUsers(""));
        mainMView.put("rptOnlineUsers", reportService.analysisOnlineUsers(""));
        mainMView.put("rptActiveUsers", reportService.analysisActiveUsers(""));

        String startDate = DateTimeUtil.getWeekBeginTimeString(false);
        String endDate = DateTimeUtil.getWeekEndTimeString(false);

        // browser pie chart data
        List<Map<String, Object>> analysisBrowser = reportService.analysisBrowser(null);
        Map<String, Long> browsers = analysisBrowser.stream().collect(Collectors.groupingBy(e -> ((String) e.get("REPORTSTRING")).split("/")[0], Collectors.summingLong(e -> (Long) e.get("REPORTCOUNT"))));
        PieChartVO pieChartVO = new PieChartVO();
        pieChartVO.setLegendData(browsers.keySet());
        List<PieData> pieDataList = new ArrayList<>();
        browsers.forEach((k, v) -> {
            PieData data = new PieData();
            data.setName(StringUtils.isNotEmpty(k) ? k : "others");
            data.setValue(v);
            pieDataList.add(data);
        });
        pieChartVO.setSeriesData(pieDataList);


        List<Map<String, Object>> appOfWeek = reportService.analysisAppOfWeek(startDate, endDate);
        List<Map<String, Object>> activeUsersOfWeek = reportService.analysisActiveUsersOfWeek(startDate, endDate);

        List<Map<String, Object>> rptMonth = reportService.analysisMonth("");


        LocalDateTime start = DateTimeUtil.getWeekBeginTime(false);
        LocalDateTime end = start.plusDays(7);


        Map<String, List<Long>> appMap = new HashMap<>();
        List<Long> rptOfWeek = new ArrayList<>();
        List<Long> activeUsersList = new ArrayList<>();
        for (LocalDateTime sd = start; !sd.isEqual(end); sd = sd.plusDays(1)) {
            Long time = DateTimeUtil.getTimestampOfDateTime(sd);
            OptionalLong rpt = rptMonth.stream().filter(m -> time == ((Date) m.get("REPORTSTRING")).getTime()).mapToLong(m -> (long) m.get("REPORTCOUNT")).findFirst();
            OptionalLong activeUsers = activeUsersOfWeek.stream().filter(m -> time == ((Date) m.get("REPORTSTRING")).getTime()).mapToLong(m -> (long) m.get("REPORTCOUNT")).findFirst();
            Map<String, Long> app = appOfWeek.stream().filter(m -> time == ((Date) m.get("REPORTSTRING")).getTime()).collect(Collectors.groupingBy(e -> e.get("APPNAME").toString(), Collectors.summingLong(e -> (Long) e.get("REPORTCOUNT"))));

            app.forEach((k, v) -> {

                List<Long> analysis = appMap.get(k);
                if (Objects.isNull(analysis)) {
                    analysis = new ArrayList<>();
                }
                analysis.add(v);
                appMap.put(k, analysis);
            });

            if (rpt.isPresent()) {
                rptOfWeek.add(rpt.getAsLong());
            } else {
                rptOfWeek.add(0L);
            }
            if (activeUsers.isPresent()) {
                activeUsersList.add(activeUsers.getAsLong());
            } else {
                activeUsersList.add(0L);
            }
        }
        List<BarChartVO> barChartVOList = new ArrayList<>();
        appMap.forEach((k,v)->{
            BarChartVO barChartVO = new BarChartVO();
            barChartVO.setName(k);
            barChartVO.setData(v);
            barChartVOList.add(barChartVO);
        });

        Map<String, List<Long>> analysis = new HashMap<>();
        analysis.put("rptOfWeek", rptOfWeek);
        analysis.put("activeUsersOfWeek", activeUsersList);

        mainMView.put("rptMonth", reportService.analysisMonth(""));
        mainMView.put("analysis", analysis);
        mainMView.put("rptDayHour", reportService.analysisDayHour(""));
        mainMView.put("rptBrowser", analysisBrowser);
        mainMView.put("pieBrowser", pieChartVO);
        mainMView.put("rptApp", barChartVOList);
        return ResponseResult.newInstance(mainMView);
    }
}
