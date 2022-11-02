package model.resolver.impl;

import model.YamlParser;
import model.hardware.Metrics;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

public class Resolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

    private final YamlParser yamlParser;

    public Resolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    public Optional<Number> resolve(Map.Entry<Metrics, Object> map) throws FileNotFoundException {
        Metrics key = map.getKey();
        Object obj = map.getValue();
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = yamlParser.getValueFromProperties(Integer.class, CPU_BOUND_YAML_KEY);
                return checkBounds((double) obj, bound);
            }
            case RAM_GB_LOAD -> {
                int bound = yamlParser.getValueFromProperties(Integer.class, RAM_BOUND_YAML_KEY);
                return checkBounds((int) obj, bound);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = yamlParser.getValueFromProperties(Integer.class, DISK_SPACE_BOUND_YAML_KEY);
                return checkBounds((int) obj, bound);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private <T extends Number> Optional<T> checkBounds(T value, T bound) {
        if ((double) value >= (double) bound) {
            return Optional.of(value);
        }
        return Optional.empty();
    }
}
