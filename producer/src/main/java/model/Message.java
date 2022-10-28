package model;

import model.hardware.Metrics;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Message {

    private UUID id;

    private ZonedDateTime dateTime;

    private Metrics metrics;

    private Object value;
}
