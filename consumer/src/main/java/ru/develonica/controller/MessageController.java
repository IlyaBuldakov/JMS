package ru.develonica.controller;

import ru.develonica.model.AlertLogWriter;
import ru.develonica.common.model.YamlParser;
import ru.develonica.model.broker.BrokerMessage;
import ru.develonica.model.broker.MetricReceiver;
import ru.develonica.model.StatusResolver;
import ru.develonica.view.ApplicationView;

import java.util.concurrent.TimeUnit;

/**
 * Class which receives a {@link BrokerMessage message} from
 * {@link MetricReceiver} and processes this incoming data.
 */
public class MessageController {

    private static final int MESSAGE_GET_REQUEST_DELAY = 300;

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    private final YamlParser yamlParser = new YamlParser();

    private final AlertLogWriter alertLogWriter = new AlertLogWriter();

    private final StatusResolver statusResolver = new StatusResolver(yamlParser, alertLogWriter);

    public MessageController(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    /**
     * Starts controller's lifecycle.
     * Handling incoming JMS messages in loop.
     */
    public void proceed() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(MESSAGE_GET_REQUEST_DELAY);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
                this.applicationView.handleMap(statusResolver.resolve(brokerMessage.value()));
            } catch (Exception exception) {
                this.applicationView.handleException(exception);
            }
        }
    }
}
