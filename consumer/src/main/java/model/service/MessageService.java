package model.service;

import model.broker.MessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MessageService {

    private final MessageReceiver messageReceiver;

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void proceed() {
        while (true) {
            delay(1000);
            messageReceiver.receive();
        }
    }

    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while sleeping.");
        }
    }
}
