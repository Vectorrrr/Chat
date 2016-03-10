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
            properties.load(new FileReader("libs/protocolConstans.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void main(String[] args){
        File file=new File("libs/protocolConstans.properties");
        System.out.println(file.getAbsolutePath());
        for(File f:file.listFiles()){
            System.out.println(f.getName());
        }
    }
}
