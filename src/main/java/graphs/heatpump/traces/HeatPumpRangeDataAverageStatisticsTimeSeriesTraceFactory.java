package graphs.heatpump.traces;

import abstracts.AbstractLineTraceFactory;
import logic.models.HeatPumpDataRange;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.charts.dataviewer.api.trace.LineTrace;
import org.charts.dataviewer.utils.TraceColour;

import java.util.List;

public class HeatPumpRangeDataAverageStatisticsTimeSeriesTraceFactory extends AbstractLineTraceFactory {
    private List<HeatPumpDataRange> dataPoints;

    public HeatPumpRangeDataAverageStatisticsTimeSeriesTraceFactory(List<HeatPumpDataRange> dataPoints) {
        super("Clean Average", TraceColour.GREEN, new LineTrace());
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

        int counter = 0;
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (HeatPumpDataRange dataPoint : dataPoints) {
            data[counter] = dataPoint.getCleanWeightedMean();
            stats.addValue(data[counter]);
            counter++;
        }

        System.out.println("fluctuation ratio:" + stats.getStandardDeviation() / stats.getMean());

        this.appendTitle(stats.getMean() + " || " + stats.getStandardDeviation() + " || " + stats.getStandardDeviation() / stats.getMean());
        return data;
    }
}
