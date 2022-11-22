package ru.develonica.model;

import ru.develonica.common.exception.IncorrectPropertiesException;
import ru.develonica.common.model.hardware.Metrics;
import ru.develonica.common.model.property.YamlParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoublePredicate;

/**
 * Class that determining the status of messages based on metrics data.
 */
public class StatusResolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

    private static final String MESSAGE_STATUS_ALERT = "ALERT";

    private static final String MESSAGE_STATUS_STABLE = "STABLE";

    private final AlertLogWriter alertLogWriter;

    private final Map<String, String> properties;

    public StatusResolver(Map<String, String> properties, AlertLogWriter alertLogWriter) {
        this.alertLogWriter = alertLogWriter;
        this.properties = properties;
    }

    /**
     * Method which gets a map of pairs (metrics and
     * their statuses) from the map of metrics.
     *
     * @param metricsMap Map of metrics.
     * @return Map of pairs: Metrics and Status.
     * @throws IOException Exception.
     */
    public HashMap<Map.Entry<Metrics, Object>, String> resolve(HashMap<Metrics, Object> metricsMap)
            throws IOException, IncorrectPropertiesException {
        HashMap<Map.Entry<Metrics, Object>, String> resultMap = new HashMap<>();
        for (Map.Entry<Metrics, Object> entry : metricsMap.entrySet()) {
            resultMap.put(
                    entry,
                    getMessageStatus(entry));
        }
        return resultMap;
    }

    /**
     * Method resolving message status.
     *
     * @param entry Entry (metric).
     * @return String (status).
     * @throws IOException Exception.
     */
    private String getMessageStatus(Map.Entry<Metrics, Object> entry) throws IOException, IncorrectPropertiesException {
        Metrics key = entry.getKey();
        Object value = entry.getValue();
        double doubleValue = Double.parseDouble(String.valueOf(value));
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = YamlParser.parsePropertyToInteger(properties.get(CPU_BOUND_YAML_KEY));
                return specifyCondition(
                        (val) -> val >= bound, doubleValue, entry);
            }
            case RAM_GB_LOAD -> {
                int bound = YamlParser.parsePropertyToInteger(properties.get(RAM_BOUND_YAML_KEY));
                return specifyCondition(
                        (val -> val >= bound), doubleValue, entry);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = YamlParser.parsePropertyToInteger(properties.get(DISK_SPACE_BOUND_YAML_KEY));
                return specifyCondition(
                        (val -> val <= bound), doubleValue, entry);
            }
        }
        return MESSAGE_STATUS_STABLE;
    }

    /**
     * Method using {@link DoublePredicate} for condition specifying.
     *
     * @param predicate Predicate (condition).
     * @param value     Value.
     * @param entry     Entry (metric).
     * @return String (status)
     * @throws IOException Exception.
     */
    private String specifyCondition(DoublePredicate predicate,
                                    double value,
                                    Map.Entry<Metrics, Object> entry) throws IOException {
        if (predicate.test(value)) {
            alertLogWriter.write(entry);
            return MESSAGE_STATUS_ALERT;
        }
        return MESSAGE_STATUS_STABLE;
    }
}

