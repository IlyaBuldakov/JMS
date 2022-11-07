package ru.develonica.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.common.view.ApplicationView;

/**
 * Main application ru.develonica.view class.
 */
public class ProducerView extends ApplicationView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProducerView.class);

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
}
