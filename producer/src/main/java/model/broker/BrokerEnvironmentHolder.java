package model.broker;

import model.YamlParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import view.ApplicationView;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;

public class BrokerEnvironmentHolder {

    private static final String TOPIC_NAME = "alerts";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static Session session;

    private static Topic topic;

    private final ApplicationView applicationView;

    private boolean isEnvironmentInitialized;

    public BrokerEnvironmentHolder(ApplicationView applicationView) {
        this.applicationView = applicationView;
    }

    public Topic getTopic() {
        if (!this.isEnvironmentInitialized) {
            initEnvironment();
        }
        return topic;
    }

    public Session getSession() {
        if (!this.isEnvironmentInitialized) {
            initEnvironment();
        }
        return session;
    }

    private void initEnvironment() {
        YamlParser yamlParser = new YamlParser();
        ConnectionFactory connectionFactory;
        try {
            String address = yamlParser.getValueFromProperties(BROKER_ADDRESS_YAML_KEY);
            connectionFactory = new ActiveMQConnectionFactory("tcp://" + address);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(TOPIC_NAME);
            this.isEnvironmentInitialized = true;
            this.applicationView.handleInfoLog("CONSUMER | Broker environment initialized");
        } catch (Exception exception) {
            this.applicationView.handleException(exception);
        }
    }
}
