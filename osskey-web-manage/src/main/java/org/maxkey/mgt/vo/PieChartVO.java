package org.maxkey.mgt.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PieChartVO {
    private Set<String> legendData;
    private List<PieData> seriesData;
}
