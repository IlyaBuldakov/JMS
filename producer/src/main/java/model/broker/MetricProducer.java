package model.broker;

import model.hardware.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MetricProducer {

    private static final Logger LOG = LoggerFactory.getLogger(MetricProducer.class);

    private final BrokerEnvironmentHolder brokerEnv = new BrokerEnvironmentHolder();

    public void send(Set<Map<Metrics, Object>> metrics) {
        try {
            BrokerMessage brokerMessage = new BrokerMessage(
                    UUID.randomUUID(),
                    ZonedDateTime.now(),
                    metrics
            );

            Session activeSession = brokerEnv.getSession();
            Topic topic = brokerEnv.getTopic();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(brokerMessage);
            byte[] brokerMessageBytes = buffer.toByteArray();

            BytesMessage message = activeSession.createBytesMessage();
            message.writeBytes(brokerMessageBytes);
            MessageProducer producer = activeSession.createProducer(topic);
            producer.send(message);
            LOG.info("Message " + brokerMessage.getId() + " | " + brokerMessage.getValue() + " send.");

            buffer.close();
            oos.close();
        } catch (JMSException | IOException exception) {
            System.out.println(exception);
        }
    }
}