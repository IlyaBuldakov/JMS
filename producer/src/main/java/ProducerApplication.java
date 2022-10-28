import model.MetricProducer;
import model.service.MessageService;

public class ProducerApplication {

    private static final MetricProducer METRIC_PRODUCER = new MetricProducer();

    public static void main(String[] args) {
        MessageService service = new MessageService(METRIC_PRODUCER);
        service.proceed();
    }
}
