package model.service;

import model.SerializablePair;
import model.broker.MetricProducer;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.impl.CpuHardwareAnalyserImpl;
import model.hardware.impl.DiskHardwareAnalyserImpl;
import model.hardware.impl.RamHardwareAnalyserImpl;
import view.ApplicationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                List<SerializablePair<Metrics, Object>> analysedInfo = new ArrayList<>();
                for (HardwareAnalyser analyser : hardwareAnalysers) {
                    analysedInfo.add(analyser.analyse());
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