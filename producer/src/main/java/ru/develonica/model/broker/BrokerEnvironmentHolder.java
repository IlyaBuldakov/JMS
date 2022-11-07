package ru.develonica.model.broker;

import ru.develonica.common.model.YamlParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;

/**
 * {@link Session} and {@link Topic} holder.
 */
public class BrokerEnvironmentHolder {

    private static final String TOPIC_NAME = "alerts";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static final String BROKER_INIT_FINISH_MESSAGE = "Broker environment initialized";

    private static final Logger LOG = LoggerFactory.getLogger(BrokerEnvironmentHolder.class);

    private static final String PROTOCOL_PREFIX = "tcp://";

    private static Session session;

    private static Topic topic;

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
        LOG.info(BROKER_INIT_FINISH_MESSAGE);
    }
}
