package ru.develonica.model.broker;

import ru.develonica.common.model.hardware.Metrics;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.UUID;

/**
 * Message from broker (JMS) representation.
 *
 * @param id       Message ID.
 * @param dateTime Send time.
 */
public record BrokerMessage(UUID id, ZonedDateTime dateTime, HashMap<Metrics, Object> value)
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
