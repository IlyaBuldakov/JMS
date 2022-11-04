package model.hardware.impl;

import java.util.Map;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

/**
 * Ram load analyser.
 */
public class RamHardwareAnalyserImpl implements HardwareAnalyser {

    private static final GlobalMemory GLOBAL_MEMORY = new SystemInfo()
            .getHardware().getMemory();

    private static final int BYTES_IN_GB = 1_073_741_824;

    @Override
    public Map.Entry<Metrics, Object> analyse() {
        return Map.entry(
                Metrics.RAM_GB_LOAD,
                ((GLOBAL_MEMORY.getTotal() - GLOBAL_MEMORY.getAvailable()) / BYTES_IN_GB));
    }
}
