package graphs.heatpump;

import abstracts.AbstractLineTraceFactory;
import abstracts.Graph;
import models.HeatPumpDataPoint;
import org.charts.dataviewer.api.data.PlotData;

import java.util.Comparator;
import java.util.List;

public class HeatPumpDataGraph extends Graph {

    private List<HeatPumpDataPoint> dataPoints;

    public HeatPumpDataGraph(List<HeatPumpDataPoint> dataPoints) {
        super("HeatPumpData", "Timestamp", "Power (kWh)");
        this.dataPoints = dataPoints;
        dataPoints.sort(Comparator.comparingLong(dataPoint -> dataPoint.timestamp));
    }


    @Override
    protected PlotData buildPlotData() {
        AbstractLineTraceFactory lineTraceFactory = new HeatPumpDataTimeSeriesTraceFactory(dataPoints);
        return new PlotData(lineTraceFactory.build());
    }
}
