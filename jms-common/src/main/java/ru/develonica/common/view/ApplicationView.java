package ru.develonica.common.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.EOFException;
import java.util.concurrent.TimeUnit;

/**
 * Main application ru.develonica.view class.
 */
public class ApplicationView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

    private static final String EXCEPTION_OUTPUT_PATTERN = "Exception: %s. %s";

    private static final int EXCEPTION_OUTPUT_DELAY = 2000;

    private static final String READING_INTERRUPTED_MSG = "Reading interrupted...";

    private static final String CONNECTION_FAILED_MSG
            = "Connection failed or there are problems with broker. Trying again...";

    private static final String INTERNAL_ERROR = "Internal Error";

    private static final String SLEEPING_INTERRUPTED = "Sleeping interrupted...";

    /**
     * Exception handler.
     *
     * @param exception Exception input.
     */
    public void handleException(Exception exception) {
        String exceptionText = EXCEPTION_OUTPUT_PATTERN.formatted(exception.toString(), exception.getMessage());
        if (exception instanceof EOFException) {
            System.out.println(READING_INTERRUPTED_MSG);
        } else if (exception instanceof JMSException) {
            try {
                System.out.println(CONNECTION_FAILED_MSG);
                TimeUnit.MILLISECONDS.sleep(EXCEPTION_OUTPUT_DELAY);
            } catch (InterruptedException ie) {
                System.out.println(INTERNAL_ERROR);
                LOG.warn(SLEEPING_INTERRUPTED);
            }
        } else {
            System.out.println(INTERNAL_ERROR);
            System.out.println(exception.getMessage());
            LOG.warn(exception.toString());
        }
        LOG.error(exceptionText);
    }
}
