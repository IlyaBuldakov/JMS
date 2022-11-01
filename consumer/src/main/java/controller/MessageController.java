package controller;

import model.broker.BrokerMessage;
import model.broker.MetricReceiver;
import view.ApplicationView;

import java.util.concurrent.TimeUnit;

public class MessageController {

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    public MessageController(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    public void proceed() {
        while (true) {
            try {
                delay(1000);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
            } catch (Exception exception) {
                this.applicationView.handleException(exception);
            }
        }
    }

    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            this.applicationView.handleException(e);
        }
    }}
