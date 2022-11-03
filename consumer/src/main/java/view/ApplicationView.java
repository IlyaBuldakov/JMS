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

    private static final String OUTPUT_PREFIX = "OUTPUT | ";

    /**
     * Outputs string message.
     *
     * @param message {@link String} message.
     */
    public void handleString(String message) {
        System.out.println(OUTPUT_PREFIX + message);
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
}
