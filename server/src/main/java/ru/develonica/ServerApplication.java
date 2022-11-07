package ru.develonica;

import ru.develonica.common.model.YamlParser;
import ru.develonica.common.view.ApplicationView;
import ru.develonica.model.BrokerStarter;

/**
 * Main server class.
 */
public class ServerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final YamlParser yamlParser = new YamlParser();

    public static void main(String[] args) {
        try {
            BrokerStarter brokerStarter = new BrokerStarter(yamlParser);
            brokerStarter.start();
            Object lock = new Object();
            // For continuous operation of the server.
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception exception) {
            APPLICATION_VIEW.handleException(exception);
        }
    }
}
