package ru.develonica.model.hardware.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.OptionalDouble;
import ru.develonica.model.hardware.HardwareAnalyser;
import ru.develonica.model.hardware.Metrics;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 * CPU load analyser.
 */
public class CpuHardwareAnalyserImpl implements HardwareAnalyser {

  /**
   * CPU instance.
   */
  private static final CentralProcessor CPU = new SystemInfo()
          .getHardware().getProcessor();

  private static final int CPU_LOAD_MAX_VALUE_PERCENT = 100;

  private static final int CPU_LOAD_GET_METRIC_DELAY = 1000;

  @Override
  public Map.Entry<Metrics, Object> analyse() {
    OptionalDouble maxCpuLoad = Arrays
            .stream(CPU.getProcessorCpuLoad(CPU_LOAD_GET_METRIC_DELAY))
            .max();
    if (maxCpuLoad.isPresent()) {
      return Map.entry(
              Metrics.CPU_PERCENT_LOAD,
              maxCpuLoad.getAsDouble() * CPU_LOAD_MAX_VALUE_PERCENT);
    }
    throw new IllegalStateException("Can't access the CPU ");
  }
}