package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.SystemInfoHolder;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.util.Map;

public class RamHardwareAnalyserImpl implements HardwareAnalyser {

    private static final GlobalMemory GLOBAL_MEMORY = SystemInfoHolder.SYSTEM_INFO
            .getHardware().getMemory();

    @Override
    public Map<Metrics, Object> analyse() {
        return Map.of(Metrics.RAM_MB_LOAD, GLOBAL_MEMORY.getAvailable());
    }
}
