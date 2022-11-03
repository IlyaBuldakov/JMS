package view;

import model.pair.SerializablePair;
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
     * Outputs new line.
     */
    public void handleNewLine() {
        System.out.println();
    }

    public void handlePairs(SerializablePair<?, ?> ... pairs) {
        for (SerializablePair<?, ?> pair : pairs) {
            System.out.println(pair);
        }
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
