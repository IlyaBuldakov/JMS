package model;

import model.hardware.Metrics;

import java.io.FileNotFoundException;
import java.util.Optional;

public class PairResolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

    private final YamlParser yamlParser;

    public PairResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    public Optional<Object> resolve(SerializablePair<Metrics, Object> map) throws FileNotFoundException {
        Metrics key = map.getKey();
        Object obj = map.getValue();
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
                return checkBounds(obj, bound);
            }
            case RAM_GB_LOAD -> {
                int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
                return checkBounds(obj, bound);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
                return checkBounds(obj, bound);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private Optional<Object> checkBounds(Object value, Integer bound) {
        if (Double.parseDouble(String.valueOf(value)) >= Double.parseDouble(String.valueOf(bound))) {
            return Optional.of(value);
        }
        return Optional.empty();
    }
}
