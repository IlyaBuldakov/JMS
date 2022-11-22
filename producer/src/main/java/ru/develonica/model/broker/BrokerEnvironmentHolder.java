package ru.develonica.model.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.common.model.broker.AbstractBrokerEnvironmentHolder;

import javax.jms.Session;
import javax.jms.Topic;
import java.util.Map;

/**
 * {@link Session} and {@link Topic} holder.
 */
public class BrokerEnvironmentHolder extends AbstractBrokerEnvironmentHolder {

    private static final Logger LOG = LoggerFactory.getLogger(BrokerEnvironmentHolder.class);

    private static final String BROKER_INIT_FINISH_MESSAGE = AbstractBrokerEnvironmentHolder
            .BROKER_INIT_FINISH_MESSAGE
            .formatted("Producer's");

    public BrokerEnvironmentHolder(Map<String, String> properties) {
        super(properties);
    }

    @Override
    protected void logInitializingFinish() {
        LOG.info(BROKER_INIT_FINISH_MESSAGE);
    }
}
