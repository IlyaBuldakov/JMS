package ru.develonica;

import ru.develonica.common.model.property.YamlParser;
import ru.develonica.controller.MessageController;
import ru.develonica.model.broker.BrokerEnvironmentHolder;
import ru.develonica.model.broker.MetricReceiver;
import ru.develonica.view.ConsumerView;

import java.util.Map;

/**
 * Main consumer class.
 */
public class ConsumerApplication {

    private static final ConsumerView APPLICATION_VIEW = new ConsumerView();

    private static final YamlParser YAML_PARSER = new YamlParser();

    public static void main(String[] args) {
        try {
            Map<String, String> properties = YAML_PARSER.tryGetAllProperties();

            BrokerEnvironmentHolder brokerEnvHolder = new BrokerEnvironmentHolder(properties);
            MetricReceiver metricReceiver = new MetricReceiver(brokerEnvHolder);

            MessageController messageController = new MessageController(
                    metricReceiver, APPLICATION_VIEW, properties);
            messageController.proceed();
        } catch (Exception exception) {
            APPLICATION_VIEW.handleException(exception);
        }
    }
}
