package ru.develonica.common.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.EOFException;
import java.util.concurrent.TimeUnit;

/**
 * Main application view class.
 */
public class BaseView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseView.class);

    private static final String EXCEPTION_OUTPUT_PATTERN = "Exception: %s. %s";

    private static final String READING_INTERRUPTED_MSG = "Reading interrupted...";

    private static final String CONNECTION_FAILED_MSG
            = "Connection failed or there are problems with broker. Trying again...";

    private static final String SLEEPING_INTERRUPTED = "Sleeping interrupted...";

    private static final int EXCEPTION_OUTPUT_DELAY = 2000;

    /**
     * Exception handler.
     *
     * @param exception Exception input.
     */
    public void handleException(Exception exception) {
        String exceptionText = EXCEPTION_OUTPUT_PATTERN.formatted(exception.toString(), exception.getMessage());
        if (exception instanceof EOFException) {
            LOG.error(READING_INTERRUPTED_MSG);
        } else if (exception instanceof JMSException) {
            try {
                LOG.error(CONNECTION_FAILED_MSG);
                TimeUnit.MILLISECONDS.sleep(EXCEPTION_OUTPUT_DELAY);
            } catch (InterruptedException ie) {
                LOG.error(SLEEPING_INTERRUPTED);
            }
        } else {
            LOG.error(exception.getMessage());
        }
        LOG.error(exceptionText);
    }
}