package model.resolver;

import model.YamlParser;
import model.hardware.Metrics;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

public class RamInfoResolver {

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private final YamlParser yamlParser = new YamlParser();

    public Optional<Map.Entry<Metrics, Integer>> resolve(int ramValue) throws FileNotFoundException {
        int bound = yamlParser.getValueFromProperties(Integer.class, RAM_BOUND_YAML_KEY);
        if (ramValue >= bound) {
            return Optional.of(Map.entry(Metrics.RAM_GB_LOAD, ramValue));
        }
        return Optional.empty();
    }
}
