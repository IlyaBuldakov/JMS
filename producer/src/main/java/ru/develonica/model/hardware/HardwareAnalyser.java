package ru.develonica.model.hardware;

import ru.develonica.common.model.hardware.Metrics;

import java.util.Map;

/**
 * Interface that analyses hardware.
 */
public interface HardwareAnalyser {

    Map.Entry<Metrics, Object> analyse();
}