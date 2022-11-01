import model.broker.BrokerEnvironmentHolder;
import model.broker.MetricReceiver;
import model.service.MessageService;
import view.ApplicationView;

public class ConsumerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final BrokerEnvironmentHolder BROKER_ENV = new BrokerEnvironmentHolder(APPLICATION_VIEW);

    private static final MetricReceiver METRIC_RECEIVER = new MetricReceiver(APPLICATION_VIEW, BROKER_ENV);

    public static void main(String[] args) {
        MessageService messageService = new MessageService(METRIC_RECEIVER, APPLICATION_VIEW);
        messageService.proceed();
    }
}
