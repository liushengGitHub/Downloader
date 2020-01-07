package liusheng.main.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Properties;

/**
 * 年: 2019  月: 12 日: 26 小时: 11 分钟: 46
 * 用户名: LiuSheng
 */

public class SystemConfigLoader {

    private static Properties properties = new Properties();

    private  static  final  Path path = Paths.get("config/conf.properties");

    static {
        if (Files.exists(path)) {
            try {
                properties.load(Files.newInputStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setProperty(String name, String value) {
        properties.setProperty(name, value);
        try {
            properties.store(Files.newOutputStream(path), DateTimeFormatter
            .ofLocalizedDate(FormatStyle.SHORT).format(LocalDate.now()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static String getPropertyOrDefault(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }
}
