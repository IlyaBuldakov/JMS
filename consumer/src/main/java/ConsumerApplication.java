import model.broker.MessageReceiver;
import model.service.MessageService;

public class ConsumerApplication {

    public static void main(String[] args) {
        MessageReceiver messageReceiver = new MessageReceiver();
        MessageService messageService = new MessageService(messageReceiver);
        messageService.proceed();
    }
}
