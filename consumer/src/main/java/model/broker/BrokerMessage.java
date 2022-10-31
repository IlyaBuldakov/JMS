package model.broker;

import model.hardware.Metrics;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record BrokerMessage(UUID id, ZonedDateTime dateTime, Set<Map<Metrics, Object>> value) implements Serializable {

    @Override
    public String toString() {
        return "BrokerMessage{" +
                "id=" + this.id +
                ", dateTime=" + this.dateTime +
                ", value=" + this.value +
                '}';
    }
}
