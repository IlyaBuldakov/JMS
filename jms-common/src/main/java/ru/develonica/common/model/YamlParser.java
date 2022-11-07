package ru.develonica.common.model;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Class that parses properties from Yaml file.
 */
public class YamlParser {

    private static final String FILE_PROPERTIES_NAME = "properties";

    private static final String EXTENSION = ".yaml";

    private final InputStream propertiesFileStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(FILE_PROPERTIES_NAME + EXTENSION);

    /**
     * Main parsing method.
     *
     * @param key Yaml key.
     * @param <T> Return type.
     * @return ? extends Object (type T). Value by key param.
     */
    public <T> T getValueFromProperties(String key) {
        Yaml yaml = new Yaml();
        Map<String, T> data = yaml.load(propertiesFileStream);
        return data.get(key);
    }
}