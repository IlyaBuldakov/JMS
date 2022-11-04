package model;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoublePredicate;
import model.hardware.Metrics;

public class StatusResolver {

  private static final String CPU_BOUND_YAML_KEY = "cpu-bound";

  private static final String RAM_BOUND_YAML_KEY = "ram-bound";

  private static final String DISK_SPACE_BOUND_YAML_KEY = "disk-bound";

  private static final String MESSAGE_STATUS_ALERT = "ALERT";

  private static final String MESSAGE_STATUS_STABLE = "STABLE";

  private final YamlParser yamlParser;

  public StatusResolver(YamlParser yamlParser) {
    this.yamlParser = yamlParser;
  }

  public HashMap<Map.Entry<Metrics, Object>, String> resolve(HashMap<Metrics, Object> metricsMap)
          throws FileNotFoundException {
    HashMap<Map.Entry<Metrics, Object>, String> resultMap = new HashMap<>();
    for (Map.Entry<Metrics, Object> entry : metricsMap.entrySet()) {
      Metrics key = entry.getKey();
      Object value = entry.getValue();
      resultMap.put(
              Map.entry(key, value),
              getMessageStatus(key, value));
    }
    return resultMap;
  }

  private String getMessageStatus(Metrics key, Object value) throws FileNotFoundException {
    double doubleValue = Double.parseDouble(String.valueOf(value));
    switch (key) {
      case CPU_PERCENT_LOAD -> {
        int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
        if (specifyCondition(
                (val) -> val >= bound, doubleValue)) {
          return MESSAGE_STATUS_ALERT;
        }
      }
      case RAM_GB_LOAD -> {
        int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
        if (specifyCondition(
                (val -> val >= bound), doubleValue)) {
          return MESSAGE_STATUS_ALERT;
        }
      }
      case DISK_GB_FREE_SPACE -> {
        int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
        if (specifyCondition(
                (val -> val <= bound), doubleValue)) {
          return MESSAGE_STATUS_ALERT;
        }
      }
    }
    return MESSAGE_STATUS_STABLE;
  }

  private boolean specifyCondition(DoublePredicate predicate, double value) {
    return predicate.test(value);
  }
}
