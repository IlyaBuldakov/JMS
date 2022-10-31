package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.util.Arrays;
import java.util.Map;

public class CpuHardwareAnalyserImpl implements HardwareAnalyser {

    private static final CentralProcessor CPU = new SystemInfo()
            .getHardware().getProcessor();

    private static final int CPU_LOAD_MAX_VALUE_PERCENT = 100;

    @Override
    public Map<Metrics, Object> analyse() {
        double[] cpuLoads = Arrays
                .stream(CPU.getProcessorCpuLoad(1000))
                .map(value -> value * CPU_LOAD_MAX_VALUE_PERCENT).toArray();
        return Map.of(
                Metrics.CPU_PERCENT_LOAD,
                cpuLoads);
    }
}