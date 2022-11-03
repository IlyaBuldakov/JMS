import controller.MessageController;
import model.YamlParser;
import model.broker.BrokerEnvironmentHolder;
import model.broker.MetricReceiver;
import view.ApplicationView;

/**
 * Main consumer class.
 */
public class ConsumerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final YamlParser YAML_PARSER = new YamlParser();

    private static final BrokerEnvironmentHolder BROKER_ENV = new BrokerEnvironmentHolder(YAML_PARSER);

    private static final MetricReceiver METRIC_RECEIVER = new MetricReceiver(BROKER_ENV);

    public static void main(String[] args) {
        MessageController messageController = new MessageController(METRIC_RECEIVER, APPLICATION_VIEW);
        messageController.proceed();
    }
}
