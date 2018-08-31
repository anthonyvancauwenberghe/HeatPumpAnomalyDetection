package logic.models;

import enums.Tag;
import models.HeatPumpDataPoint;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeatPumpDataRange {

    private List<HeatPumpDataPoint> dataPoints;

    private List<Tag> tags = new ArrayList<>();

    public HeatPumpDataRange(List<HeatPumpDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        this.sortByTimestamp();
    }

    public DateTime getStartTime() {
        return new DateTime(this.dataPoints.get(0).date.toString());
    }

    public DateTime getEndTime() {
        return new DateTime(this.dataPoints.get(this.dataPoints.size() - 1).date.toString());
    }

    public int getRangeDurationInSeconds() {
        return Seconds.secondsBetween(this.getStartTime(), getEndTime()).getSeconds();
    }

    public DateTime getMiddleTimestamp() {
        return this.getStartTime().plusSeconds(this.getRangeDurationInSeconds() / 2);
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

    public double getMean() {
        return this.getStatisticsForValues().getMean();
    }

    public double getStandardDeviation() {
        return this.getStatisticsForValues().getStandardDeviation();
    }

    public double getWeightedMean() {
        DescriptiveStatistics stats = getStatisticsObject();
        double deviation = this.getStandardDeviation();
        double mean = this.getMean();

        for (HeatPumpDataPoint dataPoint : this.dataPoints) {
            if (dataPoint.value >= mean - deviation && dataPoint.value <= mean + deviation)
                stats.addValue(dataPoint.value);
        }
        return stats.getMean();
    }

    public double getCleanWeightedMean() {
        DescriptiveStatistics stats = getStatisticsObject();
        for (HeatPumpDataPoint dataPoint : this.dataPoints) {
            if (dataPoint.value >= this.getWeightedMean())
                stats.addValue(dataPoint.value);
        }
        return stats.getMean();
    }

    public List<HeatPumpDataPoint> getDataPoints() {
        return dataPoints;
    }

    public void addTag(Tag tag) {
        if (!this.hasTag(tag))
            this.tags.add(tag);
    }

    public void removeLastTag() {
        this.tags.remove(this.tags.size() - 1);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean hasTag(Tag tag) {
        for (Tag aTag : this.tags) {
            if (aTag.getCode() == tag.getCode())
                return true;
        }
        return false;
    }

    public DescriptiveStatistics getStatisticsObject() {
        return new DescriptiveStatistics();
    }

    public DescriptiveStatistics getStatisticsForValues() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (HeatPumpDataPoint dataPoint : this.dataPoints) {
            stats.addValue(dataPoint.value);
        }
        return stats;
    }
}
