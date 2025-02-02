import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    Properties prop;
    public void getEnvVar() throws IOException {
        prop=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }
    public static void setEnvVar(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }

    public static int generateRandomId(int min, int max){
        double randomId=Math.random()*(max-min)+min;
        return (int) randomId;
    }
}
