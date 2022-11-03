package model.pair;

import java.io.Serializable;

/**
 * {@link java.util.Map.Entry} serializable substitution.
 *
 * @param <T1> Key (param1).
 * @param <T2> Value (param2).
 */
public class SerializablePair<T1, T2> implements Serializable {

    T1 key;

    T2 value;

    public SerializablePair(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    public T1 getKey() {
        return key;
    }

    public T2 getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }
}
