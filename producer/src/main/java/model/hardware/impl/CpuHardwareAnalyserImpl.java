package model.hardware.impl;

import model.hardware.HardwareAnalyser;
import model.hardware.SystemInfoHolder;
import model.hardware.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.hardware.CentralProcessor;

import java.util.Map;

public class CpuHardwareAnalyserImpl implements HardwareAnalyser {

    private static final Logger LOG = LoggerFactory.getLogger(CpuHardwareAnalyserImpl.class);

    private static final CentralProcessor CPU = SystemInfoHolder.SYSTEM_INFO.getHardware().getProcessor();

    @Override
    public Map<Metrics, Object> analyse() {
        long[] systemCpuLoadTicks = CPU.getSystemCpuLoadTicks();
        double cpuLoad = Math.max(0, CPU.getSystemCpuLoadBetweenTicks(systemCpuLoadTicks)) * 100D;
        LOG.info("CPU load: " + cpuLoad);
        return Map.of(
                Metrics.CPU_PERCENT_LOAD,
                cpuLoad);
    }
}