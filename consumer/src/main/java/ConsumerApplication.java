import model.broker.MessageReceiver;

public class ConsumerApplication {

    public static void main(String[] args) {
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.receive();
    }
}
