package graphs.heatpump;

import abstracts.AbstractLineTraceFactory;
import abstracts.Graph;
import graphs.heatpump.traces.HeatPumpDataTimeSeriesTraceFactory;
import graphs.heatpump.traces.HeatPumpRangeDataAverageStatisticsTimeSeriesTraceFactory;
import graphs.heatpump.traces.HeatPumpRangeDataAverageTimeSeriesTraceFactory;
import logic.calculator.PumpOnTresholdCalculator;
import logic.extractors.PumpUnderLoadRangeExtractor;
import logic.models.HeatPumpDataRange;
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
        AbstractLineTraceFactory lineTraceDataFactory = new HeatPumpDataTimeSeriesTraceFactory(dataPoints);

        int treshold = (new PumpOnTresholdCalculator(this.dataPoints)).calculate();
        List<HeatPumpDataRange> ranges = (new PumpUnderLoadRangeExtractor(this.dataPoints, treshold)).extract();

        dataPoints.sort(Comparator.comparingLong(dataPoint -> dataPoint.timestamp));
        ranges.sort(Comparator.comparingLong(dataPoint -> dataPoint.getMiddleTimestamp().getMillis()));


        AbstractLineTraceFactory lineTraceAverageFactory = new HeatPumpRangeDataAverageTimeSeriesTraceFactory(ranges);

        AbstractLineTraceFactory lineTraceCleanAverageFactory = new HeatPumpRangeDataAverageStatisticsTimeSeriesTraceFactory(ranges);


        return new PlotData(lineTraceDataFactory.build(), lineTraceAverageFactory.build(), lineTraceCleanAverageFactory.build());
    }
}
