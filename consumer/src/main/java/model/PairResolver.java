package model;

import model.hardware.Metrics;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Class that gets {@link SerializablePair} and returns
 * another {@link Optional} with {@link SerializablePair}.
 * This optional may contain value if some bounds were
 * overflowed and will be empty if condition is stable.
 */
public class PairResolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

    private final YamlParser yamlParser;

    public PairResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    /**
     * Main resolving method.
     *
     * @param pair {@link SerializablePair}.
     * @return Optional of {@link SerializablePair}
     * @throws FileNotFoundException Exception.
     */
    public Optional<SerializablePair<Metrics, Object>> resolve(SerializablePair<Metrics, Object> pair)
            throws FileNotFoundException {
        Metrics key = pair.getKey();
        Object obj = pair.getValue();
        switch (key) {
            case CPU_PERCENT_LOAD -> {
                int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
                return getPairIfOverflowed(obj, bound, Metrics.CPU_PERCENT_LOAD);
            }
            case RAM_GB_LOAD -> {
                int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
                return getPairIfOverflowed(obj, bound, Metrics.RAM_GB_LOAD);
            }
            case DISK_GB_FREE_SPACE -> {
                int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
                return getPairIfOverflowed(obj, bound, Metrics.DISK_GB_FREE_SPACE);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    /**
     * Comparing value and bound. Returns {@link SerializablePair} in {@link Optional}
     * if value overflowed and empty {@link Optional} if condition is stable.
     * Casting to double for saving fractional part.
     *
     * @param value Value to check.
     * @param bound Bound.
     * @param metrics {@link Metrics}.
     * @return Optional of {@link SerializablePair}.
     */
    private Optional<SerializablePair<Metrics, Object>> getPairIfOverflowed(Object value, Integer bound, Metrics metrics) {
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
