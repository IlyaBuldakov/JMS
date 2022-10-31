package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationView {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

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

    public void handleErrorLog(String message) {
        if (LOG.isErrorEnabled()) {
            LOG.error(message);
        }
    }
}
