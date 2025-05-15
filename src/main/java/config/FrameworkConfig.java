
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FrameworkConfig {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config", e);
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

}
