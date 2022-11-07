package ru.develonica.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import ru.develonica.model.broker.MetricProducer;
import ru.develonica.model.hardware.HardwareAnalyser;
import ru.develonica.model.hardware.Metrics;
import ru.develonica.model.hardware.impl.CpuHardwareAnalyserImpl;
import ru.develonica.model.hardware.impl.DiskHardwareAnalyserImpl;
import ru.develonica.model.hardware.impl.RamHardwareAnalyserImpl;
import ru.develonica.view.ApplicationView;

/**
 * The layer that uses the hardware
 * analyse layer and the broker layer.
 */
public class MessageService {

  private final ApplicationView applicationView;

  /**
   * Hardware layer (analysers).
   */
  private final HardwareAnalyser[] hardwareAnalysers = {
          new CpuHardwareAnalyserImpl(),
          new DiskHardwareAnalyserImpl(),
          new RamHardwareAnalyserImpl()
  };

  /**
   * Broker layer (metric producer which sends info to broker).
   */
  private final MetricProducer metricProducer;

  public MessageService(ApplicationView applicationView, MetricProducer metricProducer) {
    this.applicationView = applicationView;
    this.metricProducer = metricProducer;
  }

  /**
   * Service lifecycle.
   * (gets metrics from analysers with delay)
   */
  public void proceed() {
    while (true) {
      try {
        delay(300);
        HashMap<Metrics, Object> analysedInfo = new HashMap<>();
        for (HardwareAnalyser analyser : hardwareAnalysers) {
          Map.Entry<Metrics, Object> entry = analyser.analyse();
          analysedInfo.put(entry.getKey(), entry.getValue());
        }
        metricProducer.send(analysedInfo);
        this.applicationView.handleInfoLog("PRODUCER | Send analysed info");
      } catch (Exception exception) {
        this.applicationView.handleException(exception);
      }
    }
  }

  /**
   * Delay method.
   *
   * @param millis Milliseconds.
   */
  private void delay(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      this.applicationView.handleException(e);
    }
  }
}