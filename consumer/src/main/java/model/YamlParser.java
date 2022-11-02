package model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlParser {

    private static final String PATH_TO_PROPERTIES = "consumer/src/main/resources/properties.yaml";

    public <T> T getValueFromProperties(String key) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES);
        Yaml yaml = new Yaml();
        Map<String, T> data = yaml.load(inputStream);
        return data.get(key);
    }
}