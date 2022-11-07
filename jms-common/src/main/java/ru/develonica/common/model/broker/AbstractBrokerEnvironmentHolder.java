package ru.develonica.common.model.broker;

import org.apache.activemq.ActiveMQConnectionFactory;
import ru.develonica.common.model.YamlParser;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;

public abstract class AbstractBrokerEnvironmentHolder {

    private static final String TOPIC_NAME = "alerts";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static final String PROTOCOL_PREFIX = "tcp://";

    protected static final String BROKER_INIT_FINISH_MESSAGE = "%s broker environment initialized";

    private Session session;

    private Topic topic;

    private boolean isEnvironmentInitialized;

    public Topic getTopic() throws Exception {
        if (!this.isEnvironmentInitialized) {
            initEnvironment();
        }
        return topic;
    }

    public Session getSession() throws Exception {
        if (!this.isEnvironmentInitialized) {
            initEnvironment();
        }
        return session;
    }

    protected abstract void logInitializingFinish();

    /**
     * Method that initializes session and topic.
     */
    private void initEnvironment() throws Exception {
        YamlParser yamlParser = new YamlParser();
        ConnectionFactory connectionFactory;
        String address = yamlParser.getValueFromProperties(BROKER_ADDRESS_YAML_KEY);
        connectionFactory = new ActiveMQConnectionFactory(PROTOCOL_PREFIX + address);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(TOPIC_NAME);
        this.isEnvironmentInitialized = true;
        logInitializingFinish();
    }

}