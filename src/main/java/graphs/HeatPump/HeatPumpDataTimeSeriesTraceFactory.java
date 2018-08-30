package graphs.HeatPump;

import abstracts.AbstractLineTraceFactory;
import models.HeatPumpDataPoint;
import org.charts.dataviewer.api.trace.TimeSeriesTrace;
import org.charts.dataviewer.utils.TraceColour;

import java.util.List;

public class HeatPumpDataTimeSeriesTraceFactory extends AbstractLineTraceFactory {
    private List<HeatPumpDataPoint> dataPoints;

    public HeatPumpDataTimeSeriesTraceFactory(List<HeatPumpDataPoint> dataPoints) {
        super("Power Usage", TraceColour.BLUE, new TimeSeriesTrace());
        this.dataPoints = dataPoints;
    }

    @Override
    public String[] getXData() {
        String[] data = new String[dataPoints.size()];

        int counter = 0;
        for (HeatPumpDataPoint dataPoint : dataPoints) {
            data[counter] = dataPoint.date.toString();
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
