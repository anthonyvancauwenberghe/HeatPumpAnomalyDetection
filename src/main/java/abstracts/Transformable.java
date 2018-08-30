package abstracts;

import org.apache.commons.csv.CSVRecord;

public interface Transformable<T> {

    public T transform(CSVRecord record);
}
