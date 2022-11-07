package ru.develonica;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import ru.develonica.common.model.YamlParser;
import ru.develonica.view.ApplicationView;

import java.net.URI;

/**
 * Main server class.
 */
public class ServerApplication {

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final String PROTOCOL_PREFIX = "broker:(tcp://";

    public static void main(String[] args) {
        try {
            YamlParser yamlParser = new YamlParser();
            String address = yamlParser.getValueFromProperties(BROKER_ADDRESS_YAML_KEY);
            BrokerService broker = BrokerFactory.createBroker(new URI(
                    PROTOCOL_PREFIX + address + ")"));
            broker.start();
            Object lock = new Object();
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception exception) {
            APPLICATION_VIEW.handleException(exception);
        }
    }
}
