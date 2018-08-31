package graphs.heatpump.traces;

import abstracts.AbstractLineTraceFactory;
import configs.FluctuationRateConfig;
import enums.Tag;
import logic.models.HeatPumpDataRange;
import logic.taggers.InsufficientRangeTagger;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.charts.dataviewer.api.trace.ScatterTrace;
import org.charts.dataviewer.utils.TraceColour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeatPumpRangeDataErrorMarginTimeSeriesTraceFactory extends AbstractLineTraceFactory {
    private List<HeatPumpDataRange> dataPoints = new ArrayList<>();

    private List<HeatPumpDataRange> originalDataPoints = new ArrayList<>();


    public HeatPumpRangeDataErrorMarginTimeSeriesTraceFactory(List<HeatPumpDataRange> dataPoints) {
        super("Fluctuation Errors", TraceColour.RED, new ScatterTrace());
        this.dataPoints.addAll(dataPoints);
        this.originalDataPoints.addAll(dataPoints);
        this.filterUselessRanges();
    }

    private void filterUselessRanges() {
        for (Iterator<HeatPumpDataRange> it = dataPoints.iterator(); it.hasNext(); ) {
            HeatPumpDataRange dataRange = it.next();
            (new InsufficientRangeTagger(dataRange)).tag();
            if (dataRange.hasTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT) || dataRange.hasTag(Tag.INSUFFICIENT_RANGE_DURATION)) {
                it.remove();
            }
        }
    }

    @Override
    public Long[] getXData() {
        Long[] data = new Long[dataPoints.size()];


        int counter = 0;
        for (HeatPumpDataRange dataPoint : dataPoints) {
            data[counter] = dataPoint.getMiddleTimestamp().getMillis();
            counter++;
        }
        return data;
    }

    @Override
    public Double[] getYData() {
        Double[] data = new Double[dataPoints.size()];

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (HeatPumpDataRange dataPoint : originalDataPoints) {
            stats.addValue(dataPoint.getCleanWeightedMean());
        }

        int counter = 0;
        double globalWeightedMean = stats.getMean();
        for (HeatPumpDataRange dataPoint : dataPoints) {
            double weightedMean = dataPoint.getCleanWeightedMean();
            data[counter] = Math.abs((weightedMean / globalWeightedMean) - 1);
            counter++;
        }

        int errorAmount = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > FluctuationRateConfig.maxInternalFluctuationRate) {
                data[i] = this.dataPoints.get(i).getCleanWeightedMean();
                errorAmount++;
            } else {
                data[i] = 0.0;
            }
        }

        this.appendTitle(": " + errorAmount);

        return data;
    }
}
