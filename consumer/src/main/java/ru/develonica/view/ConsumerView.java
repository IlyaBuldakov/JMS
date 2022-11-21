package ru.develonica.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.develonica.common.model.hardware.Metrics;
import ru.develonica.common.view.BaseView;

import java.util.HashMap;
import java.util.Map;

/**
 * Main consumer view class, which
 * is responsible for the logic of logger
 * using and displaying information on the screen.
 */
public class ConsumerView extends BaseView {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerView.class);

    private static final String PAIR_OUTPUT_PATTERN = "%s | %s";

    /**
     * Map handler method.
     *
     * @param map Map of metrics.
     */
    public void handleMap(HashMap<Map.Entry<Metrics, Object>, String> map) {
        for (Map.Entry<Map.Entry<Metrics, Object>, String> entry : map.entrySet()) {
            LOG.info(PAIR_OUTPUT_PATTERN.formatted(entry.getKey(), entry.getValue()));
        }
        System.out.println();
    }
}
