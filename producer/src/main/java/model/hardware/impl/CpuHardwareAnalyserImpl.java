package model.hardware.impl;

import model.SerializablePair;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.util.Arrays;
import java.util.OptionalDouble;

public class CpuHardwareAnalyserImpl implements HardwareAnalyser {

    private static final CentralProcessor CPU = new SystemInfo()
            .getHardware().getProcessor();

    private static final int CPU_LOAD_MAX_VALUE_PERCENT = 100;

    @Override
    public SerializablePair<Metrics, Object> analyse() {
        OptionalDouble maxCpuLoad = Arrays
                .stream(CPU.getProcessorCpuLoad(1000))
                .max();
        if (maxCpuLoad.isPresent()) {
            return new SerializablePair<>(
                    Metrics.CPU_PERCENT_LOAD,
                    maxCpuLoad.getAsDouble() * CPU_LOAD_MAX_VALUE_PERCENT);
        }
        throw new IllegalStateException("Can't access the CPU ");
    }
}