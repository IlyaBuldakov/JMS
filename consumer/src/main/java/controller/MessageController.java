package controller;

import model.PairResolver;
import model.YamlParser;
import model.broker.BrokerMessage;
import model.broker.MetricReceiver;
import view.ApplicationView;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Class which receives a {@link BrokerMessage message} from
 * {@link MetricReceiver} and processes this incoming data.
 */
public class MessageController {

    private final MetricReceiver metricReceiver;

    private final ApplicationView applicationView;

    private final YamlParser yamlParser = new YamlParser();

    private final PairResolver pairResolver = new PairResolver(yamlParser);

    public MessageController(MetricReceiver metricReceiver, ApplicationView applicationView) {
        this.metricReceiver = metricReceiver;
        this.applicationView = applicationView;
    }

    /**
     * Starts controller's lifecycle.
     * Handling incoming JMS messages in loop.
     */
    public void proceed() {
        while (true) {
            try {
                delay(300);
                BrokerMessage brokerMessage = this.metricReceiver.receive();
                handleOptional(List.of(
                        pairResolver.resolve(brokerMessage.value()
                                .get(0)),
                        pairResolver.resolve(brokerMessage.value()
                                .get(1)),
                        pairResolver.resolve(brokerMessage.value()
                                .get(2))));
            } catch (Exception exception) {
                this.applicationView.handleException(exception);
            }
        }
    }

    /**
     * Method which checks for the presence of an Optional value
     * and outputs the result if there is a not-empty value.
     *
     * @param optionals Optional list.
     */
    private void handleOptional(List<Optional<?>> optionals) {
        for (Optional<?> optional : optionals) {
            if (optional.isPresent()) {
                this.applicationView.handleString(optional.get().toString());
                this.applicationView.handleWarnLog(optional.get());
            }
        }
    }

    /**
     * Delay method.
     *
     * @param millis Milliseconds.
     */
    private void delay(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            this.applicationView.handleException(e);
        }
    }
}
