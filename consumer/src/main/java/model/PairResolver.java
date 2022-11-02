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

    public Optional<SerializablePair<Metrics, Object>> resolve(SerializablePair<Metrics, Object> map) throws FileNotFoundException {
        Metrics key = map.getKey();
        Object obj = map.getValue();
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
                return compareWithBound(obj, bound, Metrics.CPU_PERCENT_LOAD);
            }
            case RAM_GB_LOAD -> {
                int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
                return compareWithBound(obj, bound, Metrics.RAM_GB_LOAD);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
                return compareWithBound(obj, bound, Metrics.DISK_GB_FREE_SPACE);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private Optional<SerializablePair<Metrics, Object>> compareWithBound(Object value, Integer bound, Metrics metrics) {
        double doubleValue = Double.parseDouble(String.valueOf(value));
        double doubleBound = Double.parseDouble(String.valueOf(bound));
        if (metrics == Metrics.DISK_GB_FREE_SPACE) {
            if (doubleValue <= doubleBound) {
                return Optional.of(new SerializablePair<>(metrics, value));
            }
        } else {
            if (doubleValue >= doubleBound) {
                return Optional.of(new SerializablePair<>(metrics, value));
            }
        }
        return Optional.empty();
    }
}
