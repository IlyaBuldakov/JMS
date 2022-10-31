package model.service;

import model.broker.MessageReceiver;
import view.ApplicationView;

import java.util.concurrent.TimeUnit;

public class MessageService {

    private final MessageReceiver messageReceiver;

    private final ApplicationView applicationView;

    public MessageService(MessageReceiver messageReceiver, ApplicationView applicationView) {
        this.messageReceiver = messageReceiver;
        this.applicationView = applicationView;
    }

    public void proceed() {
        while (true) {
            delay(1000);
            this.messageReceiver.receive();
        }
    }

    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            this.applicationView.handleException(e);
        }
    }
}
