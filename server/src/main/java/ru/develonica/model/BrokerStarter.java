package ru.develonica.model;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;
import java.util.Map;

public class BrokerStarter {

    private static final String BROKER_PROTOCOL = "broker:(tcp://%s)";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private final Map<String, String> properties;

    public BrokerStarter(Map<String, String> properties) {
        this.properties = properties;
    }

    public void start() throws Exception {
        String address = properties.get(BROKER_ADDRESS_YAML_KEY);
        BrokerService broker = BrokerFactory.createBroker(
                new URI(BROKER_PROTOCOL.formatted(address)));
        broker.start();
    }
}
