package model.resolver;

import model.YamlParser;
import model.hardware.Metrics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CpuInfoResolver {

    private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

    private final YamlParser yamlParser;

    public CpuInfoResolver(YamlParser yamlParser) {
        this.yamlParser = yamlParser;
    }

    public Map.Entry<Metrics, List<Double>> resolve(double[] cpusLoad) throws FileNotFoundException {
        int bound = yamlParser.getValueFromProperties(Integer.class, CPU_BOUND_YAML_KEY);
        List<Double> overflowedLoadsList = new ArrayList<>();
        for (double cpuLoad : cpusLoad) {
            if (cpuLoad >= bound) {
                overflowedLoadsList.add(cpuLoad);
            }
        }
        return Map.entry(Metrics.CPU_PERCENT_LOAD, overflowedLoadsList);
    }
}
