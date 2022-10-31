package model.broker;

import org.apache.activemq.command.ActiveMQBytesMessage;
import view.ApplicationView;

import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class MessageReceiver {

    private final ApplicationView applicationView;

    private final BrokerEnvironmentHolder brokerEnv;

    public MessageReceiver(ApplicationView applicationView, BrokerEnvironmentHolder brokerEnv) {
        this.applicationView = applicationView;
        this.brokerEnv = brokerEnv;
    }

    public void receive() {
        try {
            Session activeSession = this.brokerEnv.getSession();
            Topic topic = this.brokerEnv.getTopic();

            MessageConsumer consumer = activeSession.createConsumer(topic);
            ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) consumer.receive();

            byte[] buffer = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(buffer);

            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bis);

            BrokerMessage brokerMessage = (BrokerMessage) ois.readObject();

            this.applicationView.handleMessage(brokerMessage);

            bis.close();
            ois.close();
        } catch (Exception exception) {
            this.applicationView.handleException(exception);
        }
    }
}
