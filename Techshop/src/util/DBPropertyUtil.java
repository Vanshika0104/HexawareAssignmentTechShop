package util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {
	
	public static String getConnectionString(String fileName) {
		
		Properties prop = new Properties();
        try (InputStream input = DBPropertyUtil.class.getResourceAsStream(fileName)) {
            if (input != null) {
                prop.load(input);
                return prop.getProperty("url");
            } else {
                System.out.println("Configuration file not found: " + fileName);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

