package abstracts;

import org.charts.dataviewer.api.trace.GenericTrace;
import org.charts.dataviewer.utils.TraceColour;

public abstract class AbstractLineTraceFactory<T> {

    private String title;

    private TraceColour color;

    private GenericTrace trace;


    public AbstractLineTraceFactory(String title, TraceColour color, GenericTrace trace) {
        this.title = title;
        this.color = color;
        this.trace = trace;
    }

    public abstract T[] getXData();

    public abstract T[] getYData();

    public GenericTrace<T> build() {
        GenericTrace<T> lineTrace = this.trace;
        lineTrace.setTraceName(this.title);
        lineTrace.setxArray(this.getXData());
        lineTrace.setyArray(this.getYData());
        lineTrace.setTraceColour(this.color);
        return lineTrace;
    }
}
