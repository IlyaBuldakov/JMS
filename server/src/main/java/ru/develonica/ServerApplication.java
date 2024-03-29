package ru.develonica;

import ru.develonica.common.model.property.YamlParser;
import ru.develonica.common.view.BaseView;
import ru.develonica.model.BrokerStarter;

import java.util.Map;

/**
 * Main server class.
 */
public class ServerApplication {

    private static final BaseView APPLICATION_VIEW = new BaseView();

    private static final YamlParser yamlParser = new YamlParser();

    public static void main(String[] args) {
        try {
            Map<String, String> properties = yamlParser.tryGetAllProperties();
            BrokerStarter brokerStarter = new BrokerStarter(properties);
            brokerStarter.start();
            Thread.currentThread().join();
        } catch (Exception exception) {
            APPLICATION_VIEW.handleException(exception);
        }
    }
}
