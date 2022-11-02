package model.hardware;

import model.SerializablePair;

public interface HardwareAnalyser {

    SerializablePair<Metrics, Object> analyse();
}