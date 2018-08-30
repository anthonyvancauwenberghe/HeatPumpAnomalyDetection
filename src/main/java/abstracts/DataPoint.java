package abstracts;

import Enums.Tag;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class DataPoint {
    public final long timestamp;
    public final String index;
    public final int value;
    private List<Tag> tags;

    public final Instant date;

    public DataPoint(String index, long timestamp, int value) {
        this.timestamp = timestamp;
        this.index = index;
        this.value = value;
        this.tags = new ArrayList<>();
        this.date = Instant.ofEpochMilli(timestamp);

    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeLastTag() {
        this.tags.remove(this.tags.size() - 1);
    }

    public List getTags() {
        return this.tags;
    }
}
