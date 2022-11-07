package ru.develonica;

import ru.develonica.controller.MessageController;
import ru.develonica.model.broker.BrokerEnvironmentHolder;
import ru.develonica.model.broker.MetricReceiver;
import ru.develonica.view.ApplicationView;

/**
 * Main consumer class.
 */
public class ConsumerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final BrokerEnvironmentHolder BROKER_ENV = new BrokerEnvironmentHolder();

    private static final MetricReceiver METRIC_RECEIVER = new MetricReceiver(BROKER_ENV);

    public static void main(String[] args) {
        MessageController messageController = new MessageController(METRIC_RECEIVER, APPLICATION_VIEW);
        messageController.proceed();
    }
}
