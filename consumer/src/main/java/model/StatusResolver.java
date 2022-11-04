package model;

import java.io.IOException;
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

  private final AlertLogWriter alertLogWriter;

  public StatusResolver(YamlParser yamlParser, AlertLogWriter alertLogWriter) {
    this.yamlParser = yamlParser;
    this.alertLogWriter = alertLogWriter;
  }

  public HashMap<Map.Entry<Metrics, Object>, String> resolve(HashMap<Metrics, Object> metricsMap)
          throws IOException {
    HashMap<Map.Entry<Metrics, Object>, String> resultMap = new HashMap<>();
    for (Map.Entry<Metrics, Object> entry : metricsMap.entrySet()) {
      resultMap.put(
              entry,
              getMessageStatus(entry));
    }
    return resultMap;
  }

  private String getMessageStatus(Map.Entry<Metrics, Object> entry) throws IOException {
    Metrics key = entry.getKey();
    Object value = entry.getValue();
    double doubleValue = Double.parseDouble(String.valueOf(value));
    switch (key) {
      case CPU_PERCENT_LOAD -> {
        int bound = yamlParser.getValueFromProperties(CPU_BOUND_YAML_KEY);
        return specifyCondition(
                (val) -> val >= bound, doubleValue, entry);
      }
      case RAM_GB_LOAD -> {
        int bound = yamlParser.getValueFromProperties(RAM_BOUND_YAML_KEY);
        return specifyCondition(
                (val -> val >= bound), doubleValue, entry);
      }
      case DISK_GB_FREE_SPACE -> {
        int bound = yamlParser.getValueFromProperties(DISK_SPACE_BOUND_YAML_KEY);
        return specifyCondition(
                (val -> val <= bound), doubleValue, entry);
      }
    }
    return MESSAGE_STATUS_STABLE;
  }

  private String specifyCondition(DoublePredicate predicate, double value, Map.Entry<Metrics, Object> entry) throws IOException {
    if (predicate.test(value)) {
      alertLogWriter.write(entry);
      return MESSAGE_STATUS_ALERT;
    }
    return MESSAGE_STATUS_STABLE;
  }
}

