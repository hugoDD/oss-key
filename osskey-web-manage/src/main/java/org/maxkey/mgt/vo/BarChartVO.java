package org.maxkey.mgt.vo;

import lombok.Data;

import java.util.List;

@Data
public class BarChartVO {
   private String  name;
   private String  type="bar";
   private String  stack="'vistors";
   private List<Long> data;
   private Long  animationDuration = 6000L;
}
