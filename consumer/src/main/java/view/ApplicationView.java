package view;

import java.util.HashMap;
import java.util.Map;
import model.hardware.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application view class.
 */
public class ApplicationView {

  /**
   * Logger instance.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationView.class);

  private static final String EXCEPTION_OUTPUT_PATTERN = "Exception: %s. %s";

  private static final String PAIR_OUTPUT_PATTERN = "%s | %s";

  public void handleMap(HashMap<Map.Entry<Metrics, Object>, String> map) {
    for (Map.Entry<Map.Entry<Metrics, Object>, String> entry : map.entrySet()) {
      System.out.printf(PAIR_OUTPUT_PATTERN, entry.getKey(), entry.getValue());
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Exception handler.
   *
   * @param exception Exception input.
   */
  public void handleException(Exception exception) {
    String text = EXCEPTION_OUTPUT_PATTERN.formatted(exception.toString(), exception.getMessage());
    System.err.println(text);
    LOG.error(text);
  }
}
