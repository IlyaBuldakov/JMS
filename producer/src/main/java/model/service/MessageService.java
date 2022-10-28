package model.service;

import model.MetricProducer;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    HardwareAnalyser hardwareAnalyser;

    MetricProducer metricProducer;

    public void proceed() {
        while (true) {
            delay(1000);
            Map<Metrics, Object> analysedInfo = hardwareAnalyser.analyse();
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