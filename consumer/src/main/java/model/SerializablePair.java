package model;

import java.io.Serializable;

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
}
