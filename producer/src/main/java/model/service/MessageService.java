package model.service;

import model.MetricProducer;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.impl.CpuHardwareAnalyserImpl;
import model.hardware.impl.DiskHardwareAnalyserImpl;
import model.hardware.impl.RamHardwareAnalyserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
            delay(1000);
            Set<Map<Metrics, Object>> analysedInfo = new HashSet<>();
            for (HardwareAnalyser analyser : hardwareAnalysers) {
                analysedInfo.add(analyser.analyse());
            }
            metricProducer.send(analysedInfo);
        }
    }

    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while sleeping.");
        }
    }
}