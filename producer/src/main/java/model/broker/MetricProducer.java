package model.broker;

import model.SerializablePair;
import model.hardware.Metrics;
import view.ApplicationView;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class MetricProducer {

    private final ApplicationView applicationView;

    private final BrokerEnvironmentHolder brokerEnv;

    public MetricProducer(ApplicationView applicationView, BrokerEnvironmentHolder brokerEnv) {
        this.applicationView = applicationView;
        this.brokerEnv = brokerEnv;
    }

    public void send(List<SerializablePair<Metrics, Object>> metrics) {
        try {
            BrokerMessage brokerMessage = new BrokerMessage(
                    UUID.randomUUID(),
                    ZonedDateTime.now(),
                    metrics
            );

            Session activeSession = this.brokerEnv.getSession();
            Topic topic = this.brokerEnv.getTopic();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(brokerMessage);
            byte[] brokerMessageBytes = buffer.toByteArray();

            BytesMessage message = activeSession.createBytesMessage();
            message.writeBytes(brokerMessageBytes);
            MessageProducer producer = activeSession.createProducer(topic);
            producer.send(message);
            this.applicationView.handleInfoLog("Message %s | %s send.".formatted(brokerMessage.id(), brokerMessage.value()));

            buffer.close();
            oos.close();
        } catch (JMSException | IOException exception) {
            this.applicationView.handleException(exception);
        }
    }
}