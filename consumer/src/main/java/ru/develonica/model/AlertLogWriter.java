package ru.develonica.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

import ru.develonica.common.model.hardware.Metrics;

/**
 * Class writing alert log.
 */
public class AlertLogWriter {

    private static final Path FOLDER_NAME = Path.of("logs");

    private static final String FILE_NAME = "alerts";

    private static final String FILE_EXTENSION = ".txt";

    private static final String FULL_PATH = FOLDER_NAME + "/" + FILE_NAME + FILE_EXTENSION;

    private static final String ALERT_LOG_PATTERN = "%s | %s";

    private static BufferedWriter writer;

    /**
     * Writes alert entry in File.
     *
     * @param entry Entry (metric).
     * @throws IOException Exception.
     */
    public void write(Map.Entry<Metrics, Object> entry) throws IOException {
        if (writer == null) {
            if (!Files.exists(FOLDER_NAME)) {
                Files.createDirectory(FOLDER_NAME);
            }
            new File(FULL_PATH);
            writer = new BufferedWriter(new FileWriter(FULL_PATH, true));
        }
        writer.write(ALERT_LOG_PATTERN.formatted(entry.toString(), LocalDateTime.now())
                + System.lineSeparator());
        writer.flush();
    }
}
