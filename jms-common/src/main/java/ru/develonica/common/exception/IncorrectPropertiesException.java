package ru.develonica.common.exception;

public class IncorrectPropertiesException extends Exception {

    private static final String DEFAULT_MESSAGE = "Properties are incorrect or not found";

    public IncorrectPropertiesException() {
        super(DEFAULT_MESSAGE);
    }
}
