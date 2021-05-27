package org.maxkey.util;


import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateTimeUtilTest {

    @Test
    public void getWeekBeginTime() {
//        System.out.println(DateTimeUtil.getWeekBeginTime());
        System.out.println(DateTimeUtil.getWeekBeginTimeString(true));
        System.out.println(DateTimeUtil.getWeekEndTimeString(true));
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDate = currentDateTime.minusWeeks(1).with(DayOfWeek.SUNDAY);
        System.out.println(startDate);
    }


}
