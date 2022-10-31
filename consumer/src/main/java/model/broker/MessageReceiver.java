package model.broker;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class MessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    private final BrokerEnvironmentHolder brokerEnv = new BrokerEnvironmentHolder();

    public void receive() {
        try {
            Session activeSession = brokerEnv.getSession();
            Topic topic = brokerEnv.getTopic();

            MessageConsumer consumer = activeSession.createConsumer(topic);
            ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) consumer.receive();

            byte[] buffer = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(buffer);

            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bis);

            LOG.info("Message received " + ois.readObject());

            bis.close();
            ois.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
