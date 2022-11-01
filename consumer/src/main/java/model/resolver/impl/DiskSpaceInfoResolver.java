package model.resolver.impl;

import model.YamlParser;
import model.hardware.Metrics;
import model.resolver.Resolver;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

public class DiskSpaceInfoResolver implements Resolver<Integer, Integer> {

    private static final String DISK_SPACE_BOUND_YAML_KEY = "cpu-bound";

    private final YamlParser yamlParser;

    public DiskSpaceInfoResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    @Override
    public Optional<Map.Entry<Metrics, Integer>> resolve(Integer diskSpaceValue) throws FileNotFoundException {
        int bound = yamlParser.getValueFromProperties(Integer.class, DISK_SPACE_BOUND_YAML_KEY);
        if (diskSpaceValue >= bound) {
            return Optional.of(Map.entry(Metrics.DISK_GB_FREE_SPACE, diskSpaceValue));
        }
        return Optional.empty();
    }
}
