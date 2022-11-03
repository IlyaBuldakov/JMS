package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application view class.
 */
public class ApplicationView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

    /**
     * Outputs string message.
     *
     * @param message {@link String} message.
     */
    public void handleString(String message) {
        System.out.println(message);
    }

    /**
     * Exception handler.
     *
     * @param exception Exception input.
     */
    public void handleException(Exception exception) {
        String text = "Exception: %s. %s".formatted(exception.toString(), exception.getMessage());
        System.err.println(text);
        LOG.error(text);
    }

    /**
     * Info log handler.
     *
     * @param message {@link String} message.
     */
    public void handleInfoLog(String message) {
        if (LOG.isInfoEnabled()) {
            LOG.info(message);
        }
    }

    /**
     * Warn log handler.
     *
     * @param message {@link String} message.
     */
    public void handleWarnLog(Object message) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(String.valueOf(message));
        }
    }
}
