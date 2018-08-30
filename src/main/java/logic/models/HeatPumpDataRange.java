package logic.models;

import enums.Tag;
import models.HeatPumpDataPoint;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.Comparator;
import java.util.List;

public class HeatPumpDataRange {

    private List<HeatPumpDataPoint> dataPoints;

    private List<Tag> tags;

    public boolean inSufficientDataRange = false;

    public HeatPumpDataRange(List<HeatPumpDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        this.sortByTimestamp();
    }

    public int getRangeDurationInSeconds() {
        DateTime startRange = new DateTime(this.dataPoints.get(0).date.toString());
        DateTime endRange = new DateTime(this.dataPoints.get(this.dataPoints.size() - 1).date.toString());
        return Seconds.secondsBetween(startRange, endRange).getSeconds();
    }

    public int getRangeDurationInMinutes() {
        return this.getRangeDurationInSeconds() * 60;
    }

    public int size() {
        return this.dataPoints.size();
    }

    public void sortByTimestamp() {
        dataPoints.sort(Comparator.comparingLong(dataPoint -> dataPoint.timestamp));
    }

    public double getAverage() {
        double sum = 0;
        for (HeatPumpDataPoint dataPoint : this.dataPoints) {
            sum += dataPoint.value;
        }
        return this.dataPoints.isEmpty() ? 0 : 1.0 * sum / (this.dataPoints.size());
    }

    public List<HeatPumpDataPoint> getDataPoints() {
        return dataPoints;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeLastTag() {
        this.tags.remove(this.tags.size() - 1);
    }

    public List<Tag> getTags() {
        return tags;
    }
}
