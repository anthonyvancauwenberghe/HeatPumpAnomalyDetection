package enums;

public enum Tag {

    INSUFFICIENT_DATAPOINT_AMOUNT(0, "Insufficient DataPoints in Range To Analyze anomalies"),
    INSUFFICIENT_RANGE_DURATION(1, "Insufficient duration in Range To Analyze anomalies "),
    ;

    private final int code;

    private final String message;

    Tag(int tagCode, String message) {
        this.code = tagCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
