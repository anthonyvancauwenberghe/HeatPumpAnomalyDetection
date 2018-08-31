package abstracts;

import org.charts.dataviewer.api.trace.GenericTrace;
import org.charts.dataviewer.utils.TraceColour;

public abstract class AbstractLineTraceFactory<T> {

    private String title;

    private TraceColour color;

    protected GenericTrace trace;


    public AbstractLineTraceFactory(String title, TraceColour color, GenericTrace trace) {
        this.title = title;
        this.color = color;
        this.trace = trace;
    }

    public abstract T[] getXData();

    public abstract T[] getYData();

    public GenericTrace<T> build() {
        GenericTrace<T> trace = this.trace;
        trace.setxArray(this.getXData());
        trace.setyArray(this.getYData());
        trace.setTraceName(this.title);
        trace.setTraceColour(this.color);
        return trace;
    }

    protected void appendTitle(String message) {
        this.title = this.title + message;
    }
}
