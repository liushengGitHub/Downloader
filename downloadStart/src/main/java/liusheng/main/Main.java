package liusheng.main;

import liusheng.downloadCore.MainApplication;
import liusheng.downloadCore.config.PathConstants;
import liusheng.downloadCore.config.SystemCodeConfig;
import liusheng.main.config.ConfigConstants;
import liusheng.main.config.SystemConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws IOException {
        initFolder();
        initConfig();
        MainApplication.launch(MainApplication.class,args);

    }

    private static void initConfig() throws IOException {

        String componentSize = SystemConfigLoader.getPropertyOrDefault(ConfigConstants.COMPONENT_SIZE, "2");
        String title = SystemConfigLoader.getPropertyOrDefault(ConfigConstants.TITLE, "蝶蝶");
        String mainThreadNumber = SystemConfigLoader.getPropertyOrDefault(ConfigConstants.TASK_NUMBER, "5");
        String defaultMainBackground = SystemConfigLoader.getPropertyOrDefault(ConfigConstants.DEFAULT_MAIN_BACKGROUND, "buterfly.jpg");
        SystemCodeConfig.setComponentSize(Integer.parseInt(componentSize));
        SystemCodeConfig.setTitle(title);
        SystemCodeConfig.setMainThreadNumber(Integer.parseInt(mainThreadNumber));
        SystemCodeConfig.setDefaultMainBackground(defaultMainBackground);
        config();

    }

    private static void config() {
        Map<String, Object> map = SystemCodeConfig.properties();
        map.put("semaphore",new Semaphore(Integer.parseInt(SystemConfigLoader.getPropertyOrDefault("semaphore","1"))));

    }

    private static void initFolder() throws IOException {
        Files.createDirectories(Paths.get(PathConstants.CONFIG));
        Files.createDirectories(Paths.get(PathConstants.PLUGINS));
        Files.createDirectories(Paths.get(PathConstants.BACKGROUND));
    }
}
