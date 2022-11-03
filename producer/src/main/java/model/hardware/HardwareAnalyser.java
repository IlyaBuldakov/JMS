package model.hardware;

import model.SerializablePair;

/**
 * Interface that analyses hardware.
 */
public interface HardwareAnalyser {

    /**
     * Analyse method.
     *
     * @return {@link SerializablePair} of
     * {@link Metrics} and value ({@link Object}).
     */
    SerializablePair<Metrics, Object> analyse();
}