package model;

import model.hardware.Metrics;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BrokerMessage {

    private final UUID id;

    private final ZonedDateTime dateTime;

    private final Set<Map<Metrics, Object>> value;

    public BrokerMessage(UUID id, ZonedDateTime dateTime, Set<Map<Metrics, Object>> value ) {
        this.id = id;
        this.dateTime = dateTime;
        this.value = value;
    }
}
