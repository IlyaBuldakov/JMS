package model.pair;

public class StableConditionPair<T1, T2> extends SerializablePair<T1, T2> {

    private static final String CONDITION_MESSAGE = "STABLE | ";

    public StableConditionPair(T1 key, T2 value) {
        super(key, value);
    }

    @Override
    public String toString() {
        return CONDITION_MESSAGE + "%s %s".formatted(key, value);
    }
}
