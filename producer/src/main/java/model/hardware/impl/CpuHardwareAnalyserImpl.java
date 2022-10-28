package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.util.Map;

public class CpuHardwareAnalyserImpl implements HardwareAnalyser {

    private static final CentralProcessor CPU = new SystemInfo()
            .getHardware().getProcessor();

    @Override
    public Map<Metrics, Object> analyse() {
        long[] systemCpuLoadTicks = CPU.getSystemCpuLoadTicks();
        return Map.of(
                Metrics.CPU_PERCENT_LOAD,
                Math.max(0, CPU.getSystemCpuLoadBetweenTicks(systemCpuLoadTicks)) * 100D);
    }
}