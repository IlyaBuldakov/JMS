package ru.develonica.controller;

import ru.develonica.common.model.YamlParser;
import ru.develonica.model.AlertLogWriter;
import ru.develonica.model.StatusResolver;
import ru.develonica.model.broker.BrokerMessage;
import ru.develonica.model.broker.MetricReceiver;
import ru.develonica.view.ConsumerView;

import java.util.concurrent.TimeUnit;

/**
 * Class which receives a {@link BrokerMessage message} from
 * {@link MetricReceiver} and processes this incoming data.
 */
public class MessageController {

    private final MetricReceiver metricReceiver;

    private final ConsumerView consumerView;

    private final YamlParser yamlParser = new YamlParser();

    private final AlertLogWriter alertLogWriter = new AlertLogWriter();

    private final StatusResolver statusResolver = new StatusResolver(yamlParser, alertLogWriter);

    public MessageController(MetricReceiver metricReceiver, ConsumerView consumerView) {
        this.metricReceiver = metricReceiver;
        this.consumerView = consumerView;
    }

    /**
     * Starts controller's lifecycle.
     * Handling incoming JMS messages in loop.
     */
    public void proceed() {
        try {
            final int delay = yamlParser.getValueFromProperties("delay");
            while (true) {
                TimeUnit.MILLISECONDS.sleep(delay);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
                this.consumerView.handleMap(statusResolver.resolve(brokerMessage.value()));
            }
        } catch (Exception exception) {
            this.consumerView.handleException(exception);
        }
    }
}
