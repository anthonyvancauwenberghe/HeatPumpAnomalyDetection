package graphs.heatpump.traces;

import abstracts.AbstractLineTraceFactory;
import logic.models.HeatPumpDataRange;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.charts.dataviewer.api.trace.LineTrace;
import org.charts.dataviewer.utils.TraceColour;

import java.util.List;

public class HeatPumpRangeDataGlobalAverageTraceFactory extends AbstractLineTraceFactory {
    private List<HeatPumpDataRange> dataPoints;

    public HeatPumpRangeDataGlobalAverageTraceFactory(List<HeatPumpDataRange> dataPoints) {
        super("GLOBAL CLEAN AVERAGE", TraceColour.BLACK, new LineTrace());
        this.dataPoints = dataPoints;
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
        for (HeatPumpDataRange dataPoint : dataPoints) {
            stats.addValue(dataPoint.getCleanWeightedMean());
        }
        int counter = 0;
        for (HeatPumpDataRange dataPoint : dataPoints) {
            data[counter] = stats.getMean();
            counter++;
        }
        return data;
    }
}
