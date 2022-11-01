package model.broker;

import model.hardware.Metrics;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record BrokerMessage(UUID id, ZonedDateTime dateTime, Set<Map<Metrics, Object>> value) implements Serializable {

    @Override
    public String toString() {
        return "BrokerMessage{ id = %s | dateTime = %s, value = %s"
                .formatted(
                        this.id,
                        this.dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                        this.value);
    }
}
