package model.service;

import model.broker.MetricProducer;
import model.hardware.HardwareAnalyser;
import model.hardware.Metrics;
import model.hardware.impl.CpuHardwareAnalyserImpl;
import model.hardware.impl.DiskHardwareAnalyserImpl;
import model.hardware.impl.RamHardwareAnalyserImpl;
import view.ApplicationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MessageService {

    private final ApplicationView applicationView;

    private final HardwareAnalyser[] hardwareAnalysers = {
            new CpuHardwareAnalyserImpl(),
            new DiskHardwareAnalyserImpl(),
            new RamHardwareAnalyserImpl()
    };

    private final MetricProducer metricProducer;

    public MessageService(ApplicationView applicationView, MetricProducer metricProducer) {
        this.applicationView = applicationView;
        this.metricProducer = metricProducer;
    }

    public void proceed() {
        while (true) {
            delay(1000);
            List<Map<Metrics, Object>> analysedInfo = new ArrayList<>();
            for (HardwareAnalyser analyser : hardwareAnalysers) {
                analysedInfo.add(analyser.analyse());
            }
            metricProducer.send(analysedInfo);
            this.applicationView.handleInfoLog("PRODUCER | Send analysed info");
        }
    }

    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            this.applicationView.handleException(e);
        }
    }
}