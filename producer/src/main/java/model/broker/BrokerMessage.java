package model.broker;

import model.SerializablePair;
import model.hardware.Metrics;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.UUID;

/**
 * Message from broker (JMS) representation.
 *
 * @param id Message ID.
 * @param dateTime Send time.
 * @param value Value. List of {@link SerializablePair} (metrics reports).
 */
public record BrokerMessage(UUID id, ZonedDateTime dateTime, List<SerializablePair<Metrics, Object>> value)
        implements Serializable {

    @Override
    public String toString() {
        return "BrokerMessage{ id = %s | dateTime = %s, value = %s"
                .formatted(
                        this.id,
                        this.dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                        this.value);
    }
}
