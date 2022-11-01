package model.resolver.impl;

import model.YamlParser;
import model.hardware.Metrics;
import model.resolver.Resolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CpuInfoResolver implements Resolver<List<Double>, Double[]> {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private final YamlParser yamlParser;

    public CpuInfoResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    @Override
    public Optional<Map.Entry<Metrics, List<Double>>> resolve(Double[] cpusLoad) throws FileNotFoundException {
        int bound = yamlParser.getValueFromProperties(Integer.class, CPU_BOUND_YAML_KEY);
        List<Double> overflowedLoadsList = new ArrayList<>();
        for (double cpuLoad : cpusLoad) {
            if (cpuLoad >= bound) {
                overflowedLoadsList.add(cpuLoad);
            }
        }
        return overflowedLoadsList.size() == 0
                ? Optional.empty()
                : Optional.of(Map.entry(Metrics.CPU_PERCENT_LOAD, overflowedLoadsList));
    }
}
