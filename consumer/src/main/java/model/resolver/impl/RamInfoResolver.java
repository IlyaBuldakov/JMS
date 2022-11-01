package model.resolver.impl;

import model.YamlParser;
import model.hardware.Metrics;
import model.resolver.Resolver;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

public class RamInfoResolver implements Resolver<Integer, Integer> {

    private static final String RAM_BOUND_YAML_KEY = "ram-bound";

    private final YamlParser yamlParser = new YamlParser();

    @Override
    public Optional<Map.Entry<Metrics, Integer>> resolve(Integer ramValue) throws FileNotFoundException {
        int bound = yamlParser.getValueFromProperties(Integer.class, RAM_BOUND_YAML_KEY);
        if (ramValue >= bound) {
            return Optional.of(Map.entry(Metrics.RAM_GB_LOAD, ramValue));
        }
        return Optional.empty();
    }
}
