import model.broker.MessageReceiver;
import model.service.MessageService;

public class ConsumerApplication {

    private static final MessageReceiver messageReceiver = new MessageReceiver();

    public static void main(String[] args) {
        MessageService messageService = new MessageService(messageReceiver);
        messageService.proceed();
    }
}
