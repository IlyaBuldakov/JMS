package ru.develonica;

import ru.develonica.common.model.property.YamlParser;
import ru.develonica.model.broker.BrokerEnvironmentHolder;
import ru.develonica.model.broker.MetricProducer;
import ru.develonica.model.service.MessageService;
import ru.develonica.view.ProducerView;

import java.util.Map;

/**
 * Main producer class.
 */
public class ProducerApplication {

    private static final ProducerView APPLICATION_VIEW = new ProducerView();

    private static final YamlParser YAML_PARSER = new YamlParser();

    public static void main(String[] args) {
        try {
            Map<String, String> properties = YAML_PARSER.tryGetAllProperties();

            BrokerEnvironmentHolder brokerEnvHolder = new BrokerEnvironmentHolder(properties);
            MetricProducer metricProducer = new MetricProducer(brokerEnvHolder);

            MessageService service = new MessageService(APPLICATION_VIEW, metricProducer, properties);
            service.proceed();
        } catch (Exception exception) {
            APPLICATION_VIEW.handleException(exception);
        }
    }
}
