package logic.taggers;

import Enums.Tag;
import abstracts.Tagger;
import configs.TagConfig;
import logic.models.HeatPumpDataRange;

public class InsufficientRangeTagger extends Tagger {

    public InsufficientRangeTagger(HeatPumpDataRange range) {
        super(range);
    }

    public void tag() {
        boolean rangeSizeInsufficient = false;
        boolean rangeDurationInsufficient = false;

        if (this.range.size() < TagConfig.MinimumInsufficientRangeDataPointsAmount) {
            rangeSizeInsufficient = true;
        }

        if (this.range.getRangeDurationInMinutes() < TagConfig.MinimumInsufficientRangeDuration) {
            rangeDurationInsufficient = true;
        }

        /*for (HeatPumpDataPoint dataPoint : range.getDataPoints()) {
            if (rangeDurationInsufficient) {
                dataPoint.addTag(Tag.INSUFFICIENT_RANGE_DURATION);
            }
            if (rangeSizeInsufficient) {
                dataPoint.addTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT);
            }
        }*/

        if (rangeDurationInsufficient) {
            range.addTag(Tag.INSUFFICIENT_RANGE_DURATION);
            this.range.inSufficientDataRange = true;
        }
        if (rangeSizeInsufficient) {
            range.addTag(Tag.INSUFFICIENT_DATAPOINT_AMOUNT);
            this.range.inSufficientDataRange = true;
        }
    }
}
