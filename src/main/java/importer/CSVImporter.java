package importer;

import abstracts.CSVTransformer;
import importer.transformers.HeatPumpDataPointTransformer;
import models.HeatPumpDataPoint;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter<T> {

    private String path;

    private T typeClass;

    public CSVImporter(String path, T typeClass) {
        this.path = path;
        this.typeClass = typeClass;
    }

    public List<T> processImport() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(this.path));
        try (org.apache.commons.csv.CSVParser csvParser = new org.apache.commons.csv.CSVParser(reader, CSVFormat.DEFAULT)) {
            List<T> list = new ArrayList<>();

            CSVTransformer<T> transformer;
            if (this.typeClass instanceof HeatPumpDataPoint) {
                transformer = new HeatPumpDataPointTransformer();
            } else {
                throw new RuntimeException("No such datapoint type found");
            }

            int counter = 0;
            for (CSVRecord csvRecord : csvParser) {
                if (counter != 0)
                    list.add(transformer.transform(csvRecord));
                counter++;
            }
            return list;
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return new ArrayList<>();
    }
}
