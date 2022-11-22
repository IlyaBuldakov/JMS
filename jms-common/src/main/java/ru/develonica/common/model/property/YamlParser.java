package ru.develonica.common.model.property;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.develonica.common.exception.IncorrectPropertiesException;

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

    public Map<String, String> tryGetAllProperties() throws Exception {
        Map<String, String> properties = new ObjectMapper(new YAMLFactory())
                .readValue(propertiesFileUrl,
                        new TypeReference<>() {
                        });
        if (properties.containsValue(null)) {
            throw new IncorrectPropertiesException();
        }
        return properties;
    }

    public static int parsePropertyToInteger(String property) throws IncorrectPropertiesException {
        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException nfe) {
            throw new IncorrectPropertiesException();
        }
    }
}