package abstracts;

import logic.models.HeatPumpDataRange;

abstract public class Tagger {
    protected HeatPumpDataRange range;

    public Tagger(HeatPumpDataRange range) {
        this.range = range;
    }

    public abstract void tag();
}
