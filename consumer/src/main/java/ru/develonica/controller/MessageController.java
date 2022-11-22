package ru.develonica.controller;

import ru.develonica.common.model.property.YamlParser;
import ru.develonica.model.AlertLogWriter;
import ru.develonica.model.StatusResolver;
import ru.develonica.model.broker.BrokerMessage;
import ru.develonica.model.broker.MetricReceiver;
import ru.develonica.view.ConsumerView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class which receives a {@link BrokerMessage message} from
 * {@link MetricReceiver} and processes this incoming data.
 */
public class MessageController {

    private static final String DELAY_PROPERTY_NAME = "delay";

    private final MetricReceiver metricReceiver;

    private final ConsumerView consumerView;

    private final AlertLogWriter alertLogWriter = new AlertLogWriter();

    private final Map<String, String> properties;

    private final StatusResolver statusResolver;


    public MessageController(MetricReceiver metricReceiver,
                             ConsumerView consumerView,
                             Map<String, String> properties) {
        this.metricReceiver = metricReceiver;
        this.consumerView = consumerView;
        this.properties = properties;
        this.statusResolver = new StatusResolver(properties, alertLogWriter);
    }

    /**
     * Starts controller's lifecycle.
     * Handling incoming JMS messages in loop.
     */
    public void proceed() {
        try {
            final int delay = YamlParser.parsePropertyToInteger(properties.get(DELAY_PROPERTY_NAME));
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
