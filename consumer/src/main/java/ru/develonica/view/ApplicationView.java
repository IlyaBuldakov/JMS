package ru.develonica.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.common.model.hardware.Metrics;

import javax.jms.JMSException;
import java.io.EOFException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Main application view class.
 */
public class ApplicationView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

    private static final String EXCEPTION_OUTPUT_PATTERN = "Exception: %s. %s";

    private static final String PAIR_OUTPUT_PATTERN = "%s | %s";

    private static final int EXCEPTION_OUTPUT_DELAY = 2000;

    private static final String READING_INTERRUPTED_MSG = "Reading interrupted...";

    private static final String CONNECTION_FAILED_MSG
            = "Connection failed or there are problems with broker. Trying again...";

    private static final String INTERNAL_ERROR = "Internal Error";

    private static final String SLEEPING_INTERRUPTED = "Sleeping interrupted...";

    /**
     * Map handler method.
     *
     * @param map Map of metrics.
     */
    public void handleMap(HashMap<Map.Entry<Metrics, Object>, String> map) {
        for (Map.Entry<Map.Entry<Metrics, Object>, String> entry : map.entrySet()) {
            System.out.printf(PAIR_OUTPUT_PATTERN, entry.getKey(), entry.getValue());
            System.out.println();
        }
        System.out.println();
    }

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
            System.out.println(exception.getMessage());
        }
        LOG.error(exceptionText);
    }
}
