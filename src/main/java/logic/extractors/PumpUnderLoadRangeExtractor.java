package logic.extractors;

import logic.models.HeatPumpDataRange;
import models.HeatPumpDataPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PumpUnderLoadRangeExtractor {

    private List<HeatPumpDataPoint> dataPoints = new ArrayList<>();
    private int treshold;

    public PumpUnderLoadRangeExtractor(List<HeatPumpDataPoint> dataPoints, int treshold) {
        this.dataPoints.addAll(dataPoints);
        this.dataPoints.sort(Comparator.comparing(dataPoint -> dataPoint.timestamp));
        this.treshold = treshold;
    }

    public List<HeatPumpDataRange> extract() {
        List<HeatPumpDataRange> rangeList = new ArrayList<>();

        List<HeatPumpDataPoint> range = new ArrayList<>();
        for (HeatPumpDataPoint dataPoint : dataPoints) {
            if (dataPoint.value >= treshold) {
                range.add(dataPoint);
            } else {
                if (!range.isEmpty()) {
                    rangeList.add(new HeatPumpDataRange(range));
                    range = new ArrayList<>();
                }
            }
        }
        if (!range.isEmpty()) {
            rangeList.add(new HeatPumpDataRange(range));
        }
        return rangeList;
    }
}
