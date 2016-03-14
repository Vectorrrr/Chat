package property;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * this class allow download some properties
 * Created by igladush on 07.03.16.
 */
public class PropertiesLoader {
    private static Properties properties = new Properties();

    static {

        try {
            properties.load(new FileReader("libs/protocol.constans.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getClientAnswerDisconnect() {
        return properties.getProperty("client.answer.disconnect");
    }

    public static String getServerAnswerDisconnect() {
        return properties.getProperty("server.answer.disconnect");
    }

}
