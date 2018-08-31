package logic;

import abstracts.Calculateable;
import logic.calculator.HeatPumpFluctuationRatioCalculator;
import logic.calculator.PumpOnTresholdCalculator;
import logic.extractors.PumpUnderLoadRangeExtractor;
import logic.models.HeatPumpDataRange;
import models.HeatPumpDataPoint;

import java.util.List;

public class LogicKernel {

    private List<HeatPumpDataPoint> dataPoints;

    public LogicKernel(List<HeatPumpDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public void run() {
        int treshold = (new PumpOnTresholdCalculator(this.dataPoints)).calculate();
        List<HeatPumpDataRange> ranges = (new PumpUnderLoadRangeExtractor(this.dataPoints, treshold)).extract();

        /*//TAG DATAPOINTS THAT HAVE LIMITED DURATION OR RESOLUTION & REMOVE IF SO
        for (Iterator<HeatPumpDataRange> it = ranges.iterator(); it.hasNext(); ) {
            HeatPumpDataRange dataRange = it.next();
            (new InsufficientRangeTagger(dataRange)).tag();
            if (dataRange.hasTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT) || dataRange.hasTag(Tag.INSUFFICIENT_RANGE_DURATION)) {
                it.remove();
            }
        }

        for(HeatPumpDataRange range : ranges){

        }*/

        Calculateable<Double> fluctuationRatioCalculator = new HeatPumpFluctuationRatioCalculator(ranges);


        System.out.println("Error ratio of: " + fluctuationRatioCalculator.calculate());

    }
}
