import model.broker.BrokerEnvironmentHolder;
import model.broker.MessageReceiver;
import model.service.MessageService;
import view.ApplicationView;

public class ConsumerApplication {

    private static final ApplicationView APPLICATION_VIEW = new ApplicationView();

    private static final BrokerEnvironmentHolder BROKER_ENV = new BrokerEnvironmentHolder(APPLICATION_VIEW);

    private static final MessageReceiver MESSAGE_RECEIVER = new MessageReceiver(APPLICATION_VIEW, BROKER_ENV);

    public static void main(String[] args) {
        MessageService messageService = new MessageService(MESSAGE_RECEIVER, APPLICATION_VIEW);
        messageService.proceed();
    }
}
