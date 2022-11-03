package model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Class that parses properties from Yaml file.
 */
public class YamlParser {

    /**
     * Path to yaml file (destination).
     */
    private static final String PATH_TO_PROPERTIES = "producer/src/main/resources/properties.yaml";

    /**
     * Main parsing method.
     *
     * @param key Yaml key.
     * @return ? extends Object (type T). Value by key param.
     * @param <T> Return type.
     * @throws FileNotFoundException Exception.
     */
    public <T> T getValueFromProperties(String key) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES);
        Yaml yaml = new Yaml();
        Map<String, T> data = yaml.load(inputStream);
        return data.get(key);
    }
}