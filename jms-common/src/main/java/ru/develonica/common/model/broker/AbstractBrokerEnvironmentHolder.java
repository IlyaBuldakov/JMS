package ru.develonica.common.model.broker;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.Map;

/**
 * Abstract class-parent of broker environment holders.
 */
public abstract class AbstractBrokerEnvironmentHolder {

    protected static final String BROKER_INIT_FINISH_MESSAGE = "%s broker environment initialized";

    private static final String TOPIC_NAME = "alerts";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static final String PROTOCOL_PREFIX = "tcp://";

    private final Map<String, String> properties;

    private Session session;

    private Topic topic;

    private boolean isEnvironmentInitialized;

    protected AbstractBrokerEnvironmentHolder(Map<String, String> properties) {
        this.properties = properties;
    }

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
        ConnectionFactory connectionFactory;
        String address = properties.get(BROKER_ADDRESS_YAML_KEY);
        connectionFactory = new ActiveMQConnectionFactory(PROTOCOL_PREFIX + address);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(TOPIC_NAME);
        this.isEnvironmentInitialized = true;
        logInitializingFinish();
    }

}