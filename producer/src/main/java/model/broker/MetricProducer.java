package model.broker;

import model.pair.SerializablePair;
import model.hardware.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.BytesMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Class that creates {@link BrokerMessage}, translates
 * it to byte array and sends it to broker.
 */
public class MetricProducer {

    private static final Logger LOG = LoggerFactory.getLogger(MetricProducer.class);

    private final BrokerEnvironmentHolder brokerEnv;

    public MetricProducer(BrokerEnvironmentHolder brokerEnv) {
        this.brokerEnv = brokerEnv;
    }

    /**
     * Send method.
     *
     * @param metrics Metric info (list of pairs).
     */
    public void send(List<SerializablePair<Metrics, Object>> metrics) throws Exception {
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
        LOG.info("Message %s | %s send.".formatted(brokerMessage.id(), brokerMessage.value()));

        buffer.close();
        oos.close();
    }
}