package ru.develonica.model;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import ru.develonica.common.model.property.YamlParser;

import java.net.URI;

public class BrokerStarter {

    private static final String BROKER_PROTOCOL = "broker:(tcp://%s)";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private final YamlParser yamlParser;

    public BrokerStarter(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    public void start() throws Exception {
        String address = yamlParser.getValueFromProperties(BROKER_ADDRESS_YAML_KEY);
        BrokerService broker = BrokerFactory.createBroker(
                new URI(BROKER_PROTOCOL.formatted(address)));
        broker.start();
    }
}
