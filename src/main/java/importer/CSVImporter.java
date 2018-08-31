package importer;

import abstracts.CSVTransformer;
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

    private CSVTransformer transformer;

    public CSVImporter(String path, CSVTransformer transformer) {
        this.path = path;
        this.transformer = transformer;
    }

    public List<T> processImport() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(this.path));
        try (org.apache.commons.csv.CSVParser csvParser = new org.apache.commons.csv.CSVParser(reader, CSVFormat.DEFAULT)) {
            List<T> list = new ArrayList<>();

            int counter = 0;
            for (CSVRecord csvRecord : csvParser) {
                if (counter != 0)
                    list.add((T) transformer.transform(csvRecord));
                counter++;
            }
            return list;
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return new ArrayList<>();
    }
}
