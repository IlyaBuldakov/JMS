import model.broker.BrokerEnvironmentHolder;
import model.broker.MetricProducer;
import model.service.MessageService;
import view.ApplicationView;

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
