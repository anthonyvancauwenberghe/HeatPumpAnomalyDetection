package importer.transformers;

import abstracts.CSVTransformer;
import models.HeatPumpDataPoint;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;

public class HeatPumpDataPointTransformer2 extends CSVTransformer {
    @Override
    public HeatPumpDataPoint transform(CSVRecord record) {
        String[] aRecord = record.get(0).split(";");
        DateTime timestamp = Instant.parse(aRecord[0], DateTimeFormat.forPattern("yyyy-MMM-dd HH:mm:ss")).toDateTime();
        int value = (int) Double.parseDouble(aRecord[1]);
        return new HeatPumpDataPoint("", timestamp.getMillis(), value);
    }
}
