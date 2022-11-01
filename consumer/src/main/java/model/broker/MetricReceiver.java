package model.broker;

import org.apache.activemq.command.ActiveMQBytesMessage;

import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class MetricReceiver {

    private final BrokerEnvironmentHolder brokerEnv;

    public MetricReceiver(BrokerEnvironmentHolder brokerEnv) {
        this.brokerEnv = brokerEnv;
    }

    public BrokerMessage receive() throws Exception {
        Session activeSession = this.brokerEnv.getSession();
        Topic topic = this.brokerEnv.getTopic();

        MessageConsumer consumer = activeSession.createConsumer(topic);
        ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) consumer.receive();

        byte[] buffer = new byte[(int) bytesMessage.getBodyLength()];
        bytesMessage.readBytes(buffer);

        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        ObjectInputStream ois = new ObjectInputStream(bis);

        bis.close();
        ois.close();

        return (BrokerMessage) ois.readObject();
    }
}
