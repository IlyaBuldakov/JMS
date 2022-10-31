package ru.develonica;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.model.YamlParser;

import java.net.URI;

public class ServerApplication {

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static final Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        try {
            YamlParser yamlParser = new YamlParser();
            String address = yamlParser.getValueFromProperties(String.class, BROKER_ADDRESS_YAML_KEY);
            BrokerService broker = BrokerFactory.createBroker(new URI(
                    "broker:(tcp://" + address + ")"));
            broker.start();
            Object lock = new Object();
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
    }
}
