package logic.taggers;

import enums.Tag;
import abstracts.Tagger;
import configs.TagConfig;
import logic.models.HeatPumpDataRange;

public class InsufficientRangeTagger extends Tagger {

    public InsufficientRangeTagger(HeatPumpDataRange range) {
        super(range);
    }

    public void tag() {

        if (this.range.size() <= TagConfig.MinimumInsufficientRangeDataPointsAmount) {
            range.addTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT);
        }

        if (this.range.getRangeDurationInMinutes() <= TagConfig.MinimumInsufficientRangeDuration) {
            range.addTag(Tag.INSUFFICIENT_RANGE_DURATION);
        }

        /*for (HeatPumpDataPoint dataPoint : range.getDataPoints()) {
            if (rangeDurationInsufficient) {
                dataPoint.addTag(Tag.INSUFFICIENT_RANGE_DURATION);
            }
            if (rangeSizeInsufficient) {
                dataPoint.addTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT);
            }
        }*/
    }
}
