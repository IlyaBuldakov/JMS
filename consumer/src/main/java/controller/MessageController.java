package controller;

import model.YamlParser;
import model.broker.BrokerMessage;
import model.broker.MetricReceiver;
import model.resolver.impl.Resolver;
import view.ApplicationView;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MessageController {

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    private final YamlParser yamlParser = new YamlParser();

    private final Resolver resolver = new Resolver(yamlParser);

    public MessageController(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    public void proceed() {
        while (true) {
            try {
                delay(1000);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
                handleOptional(
                        resolver.resolve(brokerMessage.value()
                        .get(0)),
                        resolver.resolve(brokerMessage.value()
                        .get(1)),
                        resolver.resolve(brokerMessage.value()
                        .get(2)));
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
    }

    private void handleOptional(Optional<?>... optionals) {
        for (Optional<?> optional : optionals) {
            optional.ifPresent(this.applicationView::handleWarnLog);
        }
    }
}
