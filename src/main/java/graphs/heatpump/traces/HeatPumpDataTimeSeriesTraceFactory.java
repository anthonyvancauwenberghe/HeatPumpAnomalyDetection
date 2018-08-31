package graphs.heatpump.traces;

import abstracts.AbstractLineTraceFactory;
import models.HeatPumpDataPoint;
import org.charts.dataviewer.api.trace.LineTrace;
import org.charts.dataviewer.utils.TraceColour;

import java.util.List;

public class HeatPumpDataTimeSeriesTraceFactory extends AbstractLineTraceFactory {
    private List<HeatPumpDataPoint> dataPoints;

    public HeatPumpDataTimeSeriesTraceFactory(List<HeatPumpDataPoint> dataPoints) {
        super("Power Usage", TraceColour.BLUE, new LineTrace());
        this.dataPoints = dataPoints;
    }

    @Override
    public Long[] getXData() {
        Long[] data = new Long[dataPoints.size()];

        int counter = 0;
        for (HeatPumpDataPoint dataPoint : dataPoints) {
            data[counter] = dataPoint.date.toEpochMilli();
            counter++;
        }
        return data;
    }

    @Override
    public Double[] getYData() {
        Double[] data = new Double[dataPoints.size()];

        int counter = 0;
        for (HeatPumpDataPoint dataPoint : dataPoints) {
            data[counter] = (double) dataPoint.value;
            counter++;
        }
        return data;
    }
}
