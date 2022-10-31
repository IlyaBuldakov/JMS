package model.broker;

import model.YamlParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;

public class BrokerEnvironmentHolder {

    private static final Logger LOG = LoggerFactory.getLogger(BrokerEnvironmentHolder.class);

    private static final String TOPIC_NAME = "alerts";

    private static final String BROKER_ADDRESS_YAML_KEY = "address";

    private static Session session;

    private static Topic topic;

    private boolean isEnvironmentInitialized;

    public Topic getTopic() {
        if (!isEnvironmentInitialized) {
            initEnvironment();
        }
        return topic;
    }

    public Session getSession() {
        if (!isEnvironmentInitialized) {
            initEnvironment();
        }
        return session;
    }

    private void initEnvironment() {
        YamlParser yamlParser = new YamlParser();
        ConnectionFactory connectionFactory;
        try {
            String address = yamlParser.getValueFromProperties(String.class, BROKER_ADDRESS_YAML_KEY);
            connectionFactory = new ActiveMQConnectionFactory("tcp://" + address);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(TOPIC_NAME);
            isEnvironmentInitialized = true;
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
    }
}
