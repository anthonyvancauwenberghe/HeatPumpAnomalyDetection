package models;

import abstracts.DataPoint;

public class HeatPumpDataPoint extends DataPoint {
    public HeatPumpDataPoint(String index,long timestamp, int value) {
        super(index,timestamp, value);
    }
}
