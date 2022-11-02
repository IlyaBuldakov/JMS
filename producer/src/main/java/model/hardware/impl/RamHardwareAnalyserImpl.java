package model.hardware.impl;

import model.SerializablePair;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class RamHardwareAnalyserImpl implements HardwareAnalyser {

    private static final GlobalMemory GLOBAL_MEMORY = new SystemInfo()
            .getHardware().getMemory();

    private static final int BYTES_IN_GB = 1_073_741_824;

    @Override
    public SerializablePair<Metrics, Object> analyse() {
        return new SerializablePair<>(Metrics.RAM_GB_LOAD,
                ((GLOBAL_MEMORY.getTotal() - GLOBAL_MEMORY.getAvailable()) / BYTES_IN_GB));
    }
}
