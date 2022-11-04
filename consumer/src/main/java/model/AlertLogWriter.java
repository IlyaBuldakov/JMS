package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import model.hardware.Metrics;

public class AlertLogWriter {

  private static final Path FOLDER_NAME = Path.of("logs");

  private static final String FILE_NAME = "alerts";

  private static final String FILE_EXTENSION = ".txt";

  private static BufferedWriter writer;

  public void write(Map.Entry<Metrics, Object> entry) throws IOException {
    if (writer == null) {
      Files.createDirectory(FOLDER_NAME);
      String fullPath = FOLDER_NAME + "/" + FILE_NAME + FILE_EXTENSION;
      new File(fullPath);
      writer = new BufferedWriter(new FileWriter(fullPath, true));
    }
    writer.write("%s | %s".formatted(entry.toString(), LocalDateTime.now())
            + System.lineSeparator());
    writer.flush();
  }
}
