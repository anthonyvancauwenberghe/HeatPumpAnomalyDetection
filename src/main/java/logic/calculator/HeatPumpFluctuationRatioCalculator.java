package logic.calculator;

import abstracts.Calculateable;
import logic.models.HeatPumpDataRange;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class HeatPumpFluctuationRatioCalculator implements Calculateable {

    private List<HeatPumpDataRange> ranges;

    public HeatPumpFluctuationRatioCalculator(List<HeatPumpDataRange> ranges) {
        this.ranges = ranges;
    }

    public Double calculate(){
        Double[] data = new Double[ranges.size()];

        int counter = 0;
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (HeatPumpDataRange dataRange : ranges) {
            data[counter] = dataRange.getCleanWeightedMean();
            stats.addValue(data[counter]);
            counter++;
        }

        return  stats.getStandardDeviation() / stats.getMean();
    }
}
