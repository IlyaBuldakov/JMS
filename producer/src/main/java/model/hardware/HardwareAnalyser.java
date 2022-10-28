package model.hardware;

import java.util.Map;

public interface HardwareAnalyser {

    Map<Metrics, Object> analyse();
}