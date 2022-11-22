package ru.develonica.model.service;

import ru.develonica.common.model.hardware.Metrics;
import ru.develonica.common.model.property.YamlParser;
import ru.develonica.model.broker.MetricProducer;
import ru.develonica.model.hardware.HardwareAnalyser;
import ru.develonica.model.hardware.impl.CpuHardwareAnalyserImpl;
import ru.develonica.model.hardware.impl.DiskHardwareAnalyserImpl;
import ru.develonica.model.hardware.impl.RamHardwareAnalyserImpl;
import ru.develonica.view.ProducerView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The layer that uses the hardware
 * analyse layer and the broker layer.
 */
public class MessageService {

    private static final String PRODUCER_SEND_MSG = "PRODUCER | Send analysed info";

    private static final String DELAY_PROPERTY_NAME = "delay";

    private final ProducerView producerView;

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

    /**
     * Application properties.
     */
    private final Map<String, String> properties;

    public MessageService(ProducerView producerView,
                          MetricProducer metricProducer,
                          Map<String, String> properties) {
        this.producerView = producerView;
        this.metricProducer = metricProducer;
        this.properties = properties;
    }

    /**
     * Service lifecycle.
     * (gets metrics from analysers with delay)
     */
    public void proceed() {
        try {
            final int delay = YamlParser
                    .parsePropertyToInteger(properties.get(DELAY_PROPERTY_NAME));
            while (true) {
                TimeUnit.MILLISECONDS.sleep(delay);
                HashMap<Metrics, Object> analysedInfo = new HashMap<>();
                for (HardwareAnalyser analyser : hardwareAnalysers) {
                    Map.Entry<Metrics, Object> entry = analyser.analyse();
                    analysedInfo.put(entry.getKey(), entry.getValue());
                }
                metricProducer.send(analysedInfo);
                this.producerView.handleInfoLog(PRODUCER_SEND_MSG);
            }
        } catch (Exception exception) {
            this.producerView.handleException(exception);
        }
    }
}