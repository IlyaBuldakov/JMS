package view;

import model.broker.BrokerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationView {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

    public void handleMessage(BrokerMessage brokerMessage) {
        System.out.println(brokerMessage);
    }

    public void handleException(Exception exception) {
        String text = "Exception: %s. %s".formatted(exception.toString(), exception.getMessage());
        System.err.println(text);
        LOG.error(text);
    }

    public void handleInfoLog(String message) {
        if (LOG.isInfoEnabled()) {
            LOG.info(message);
        }
    }

    public void handleWarnLog(String message) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(message);
        }
    }

    public void handleErrorLog(String message) {
        if (LOG.isErrorEnabled()) {
            LOG.error(message);
        }
    }
}
