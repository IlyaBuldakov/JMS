package ru.develonica.common.model.property;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Class that parses properties from Yaml file.
 */
public class YamlParser {

    private static final String FILE_PROPERTIES_NAME = "properties";

    private static final String EXTENSION = ".yaml";

    private final URL propertiesFileUrl = getClass()
            .getClassLoader()
            .getResource(FILE_PROPERTIES_NAME + EXTENSION);

    /**
     * Main parsing method.
     *
     * @param key Yaml key.
     * @param <T> Return type.
     * @return ? extends Object (type T). Value by key param.
     */
    public <T> T getValueFromProperties(String key) throws IOException {
        Map<String, T> properties
                = new ObjectMapper(new YAMLFactory()).readValue(propertiesFileUrl, new TypeReference<>() {
        });
        return properties.get(key);
    }
}