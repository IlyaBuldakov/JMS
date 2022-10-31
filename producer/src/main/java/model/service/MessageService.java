package model.service;

import model.broker.MetricProducer;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.impl.CpuHardwareAnalyserImpl;
import model.hardware.impl.DiskHardwareAnalyserImpl;
import model.hardware.impl.RamHardwareAnalyserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private final HardwareAnalyser[] hardwareAnalysers = {
            new CpuHardwareAnalyserImpl(),
            new DiskHardwareAnalyserImpl(),
            new RamHardwareAnalyserImpl()
    };

    private final MetricProducer metricProducer;

    public MessageService(MetricProducer metricProducer) {
        this.metricProducer = metricProducer;
    }

    public void proceed() {
        while (true) {
            Set<Map<Metrics, Object>> analysedInfo = new LinkedHashSet<>();
            for (HardwareAnalyser analyser : hardwareAnalysers) {
                analysedInfo.add(analyser.analyse());
            }
            metricProducer.send(analysedInfo);
        }
    }
}