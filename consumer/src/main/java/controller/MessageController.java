package controller;

import model.YamlParser;
import model.broker.BrokerMessage;
import model.broker.MetricReceiver;
import model.PairResolver;
import view.ApplicationView;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MessageController {

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    private final YamlParser yamlParser = new YamlParser();

    private final PairResolver pairResolver = new PairResolver(yamlParser);

    public MessageController(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    public void proceed() {
        while (true) {
            try {
                delay(300);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
                handleOptional(
                        pairResolver.resolve(brokerMessage.value()
                        .get(0)),
                        pairResolver.resolve(brokerMessage.value()
                        .get(1)),
                        pairResolver.resolve(brokerMessage.value()
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
