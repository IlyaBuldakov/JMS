package model.pair;

import model.hardware.Metrics;

public class BoundOverflowPair extends SerializablePair<Metrics, Object> {

    private static final String CONDITION_MESSAGE = "ALERT  | ";

    public BoundOverflowPair(Metrics key, Object value) {
        super(key, value);
    }

    @Override
    public String toString() {
        return CONDITION_MESSAGE + "%s %s".formatted(key, value);
    }
}
