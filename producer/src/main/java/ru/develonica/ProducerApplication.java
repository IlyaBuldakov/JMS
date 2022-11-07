package ru.develonica;

import ru.develonica.model.broker.BrokerEnvironmentHolder;
import ru.develonica.model.broker.MetricProducer;
import ru.develonica.model.service.MessageService;
import ru.develonica.view.ApplicationView;

/**
 * Main producer class.
 */
public class ProducerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final BrokerEnvironmentHolder BROKER_ENV = new BrokerEnvironmentHolder();

    private static final MetricProducer METRIC_PRODUCER = new MetricProducer(BROKER_ENV);

    public static void main(String[] args) {
        MessageService service = new MessageService(APPLICATION_VIEW, METRIC_PRODUCER);
        service.proceed();
    }
}