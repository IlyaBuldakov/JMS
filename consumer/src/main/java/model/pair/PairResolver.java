package model.pair;

import model.YamlParser;
import model.hardware.Metrics;

import java.io.FileNotFoundException;

public class PairResolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

    private final YamlParser yamlParser;

    public PairResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    public SerializablePair<Metrics, Object> resolve(SerializablePair<Metrics, Object> pair)
            throws FileNotFoundException {
        Metrics key = pair.getKey();
        Object value = pair.getValue();
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
                return getRightTypeOfPair(value, bound, Metrics.CPU_PERCENT_LOAD);
            }
            case RAM_GB_LOAD -> {
                int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
                return getRightTypeOfPair(value, bound, Metrics.RAM_GB_LOAD);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
                return getRightTypeOfPair(value, bound, Metrics.DISK_GB_FREE_SPACE);
            }
            default -> {
                return new StableConditionPair(key, value);
            }
        }
    }

    private SerializablePair<Metrics, Object> getRightTypeOfPair(Object value, Integer bound, Metrics metrics) {
        double doubleValue = Double.parseDouble(String.valueOf(value));
        double doubleBound = Double.parseDouble(String.valueOf(bound));
        if (metrics == Metrics.DISK_GB_FREE_SPACE) {
            if (doubleValue <= doubleBound) {
                return new BoundOverflowPair(metrics, value);
            }
        } else {
            if (doubleValue >= doubleBound) {
                return new BoundOverflowPair(metrics, value);
            }
        }
        return new StableConditionPair(metrics, value);
    }
}
