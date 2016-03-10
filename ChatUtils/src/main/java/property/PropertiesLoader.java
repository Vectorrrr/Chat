package property;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by igladush on 07.03.16.
 */
public class PropertiesLoader {
    public static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader("protocolConstans.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
