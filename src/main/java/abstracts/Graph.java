package abstracts;

import org.charts.dataviewer.DataViewer;
import org.charts.dataviewer.api.config.DataViewerConfiguration;
import org.charts.dataviewer.api.data.PlotData;

public abstract class Graph {

    private String title;

    private String xAxisTitle;

    private String yAxisTitle;

    public Graph(String title, String xAxisTitle, String yAxisTitle) {
        this.title = title;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
    }

    public void render() {

        // Create dataviewer
        DataViewer dataviewer = new DataViewer();

        // Create dataviewer configuration
        DataViewerConfiguration config = new DataViewerConfiguration();
        // Plot title
        config.setPlotTitle(this.title);
        // X axis title
        config.setxAxisTitle(this.xAxisTitle);
        // Y axis title
        config.setyAxisTitle(this.yAxisTitle);

        config.setMarginBottom(200);

        // Update the configuration
        dataviewer.updateConfiguration(config);

        // Container of traces
        PlotData plotData = this.buildPlotData();

        // Plot all traces in the container.
        dataviewer.updatePlot(plotData);
    }

    protected abstract PlotData buildPlotData();


}
