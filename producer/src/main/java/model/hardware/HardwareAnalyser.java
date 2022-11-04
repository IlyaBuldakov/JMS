package model.hardware;

import java.util.Map;

/**
 * Interface that analyses hardware.
 */
public interface HardwareAnalyser {

    Map.Entry<Metrics, Object> analyse();
}