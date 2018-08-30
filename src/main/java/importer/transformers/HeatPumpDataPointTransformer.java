package importer.transformers;

import abstracts.CSVTransformer;
import models.HeatPumpDataPoint;
import org.apache.commons.csv.CSVRecord;

public class HeatPumpDataPointTransformer extends CSVTransformer {
    @Override
    public HeatPumpDataPoint transform(CSVRecord record) {
        String[] aRecord = record.get(0).split(";");
        long timestamp = Long.parseLong(aRecord[0]);
        int value = Integer.parseInt(aRecord[2]);
        String index = aRecord[3];
        return new HeatPumpDataPoint(index, timestamp, value);
    }
}
