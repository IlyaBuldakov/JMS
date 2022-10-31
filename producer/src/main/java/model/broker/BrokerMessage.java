package model.broker;

import model.hardware.Metrics;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BrokerMessage implements Serializable {

    private final UUID id;

    private final ZonedDateTime dateTime;

    private final Set<Map<Metrics, Object>> value;

    public BrokerMessage(UUID id, ZonedDateTime dateTime, Set<Map<Metrics, Object>> value ) {
        this.id = id;
        this.dateTime = dateTime;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Set<Map<Metrics, Object>> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BrokerMessage{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", value=" + value +
                '}';
    }
}
