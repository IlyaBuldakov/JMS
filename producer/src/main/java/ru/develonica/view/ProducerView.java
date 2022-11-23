package ru.develonica.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.common.view.BaseView;

/**
 * Main producer view class, which
 * is responsible for the logic of logger
 * using and displaying information on the screen.
 */
public class ProducerView extends BaseView {

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
        LOG.info(message);
    }
}
