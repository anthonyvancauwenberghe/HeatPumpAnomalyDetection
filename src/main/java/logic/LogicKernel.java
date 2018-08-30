package logic;

import logic.calculator.PumpOnTresholdCalculator;
import logic.extractors.PumpUnderLoadRangeExtractor;
import logic.models.HeatPumpDataRange;
import logic.taggers.InsufficientRangeTagger;
import models.HeatPumpDataPoint;

import java.util.Iterator;
import java.util.List;

public class LogicKernel {

    private List<HeatPumpDataPoint> dataPoints;

    public LogicKernel(List<HeatPumpDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public void run() {
        int treshold = (new PumpOnTresholdCalculator(this.dataPoints)).calculate();
        List<HeatPumpDataRange> ranges = (new PumpUnderLoadRangeExtractor(this.dataPoints, treshold)).extract();

        //TAG DATAPOINTS THAT HAVE LIMITED DURATION OR RESOLUTION & REMOVE IF SO
        for (Iterator<HeatPumpDataRange> it = ranges.iterator(); it.hasNext(); ) {
            HeatPumpDataRange dataRange = it.next();
            (new InsufficientRangeTagger(dataRange)).tag();
            if (dataRange.inSufficientDataRange) {
                it.remove();
            }
        }

        for(HeatPumpDataRange range : ranges){

        }

        System.out.println("here");

    }
}
