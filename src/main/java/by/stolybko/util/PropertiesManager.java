package by.stolybko.util;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class PropertiesManager {

    private static final String CONFIG_FILE = "application.yml";
    private static Map<String, Map<String, Object>> config;

    static {
        loadConfig();
    }

    private PropertiesManager() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static void loadConfig() {
        try (InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            config = yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getProperty(String key) {
        return config.get(key);
    }

}
