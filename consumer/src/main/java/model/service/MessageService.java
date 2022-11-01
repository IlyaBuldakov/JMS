package model.service;

import model.broker.MetricReceiver;
import view.ApplicationView;

import java.util.concurrent.TimeUnit;

public class MessageService {

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    public MessageService(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    public void proceed() {
        while (true) {
            delay(1000);
            this.metricReceiver.receive();
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
