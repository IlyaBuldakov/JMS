package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import model.hardware.Metrics;

public class AlertLogWriter {

  private static final String DESTINATION = "log.txt";

  private static BufferedWriter writer;

  public void write(Map.Entry<Metrics, Object> entry) throws IOException {
    if (writer == null) {
      new File(DESTINATION);
      writer = new BufferedWriter(new FileWriter(DESTINATION, true));
    }
    writer.write("%s | %s".formatted(entry.toString(), LocalDateTime.now())
            + System.lineSeparator());
    writer.flush();
  }
}
