package liusheng.downloadCore.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 年: 2019  月: 12 日: 25 小时: 11 分钟: 13
 * 用户名: LiuSheng
 */

public class SystemCodeConfig {

    private static String defaultMainBackground = "buterfly.jpg";
    private static String title = "蝶蝶蝶蝶蝶蝶蝶蝶蝶蝶蝶蝶蝶蝶";
    private static int taskNumber = 5;
    private static int mainThreadNumber = 0;
    private static int helpThreadNumber = 0;

    /**
     * 主界面中组件的数量
     */
    private static int componentSize = 2;
    /**
     * 菜单的高度
     */
    private static int menuBarHeight = 30;
    /**
     * Select 的高度
     */
    private static int selectHeight = 50;


    private static double opacity = 0.8;

    private static String webDriverPath = "driver/IEDriverServer.exe";

    public static String getWebDriverPath() {
        return webDriverPath;
    }

    public static void setWebDriverPath(String webDriverPath) {
        SystemCodeConfig.webDriverPath = webDriverPath;
    }

    public static double opacity() {
        return opacity;
    }

    public static void setOpacity(double opacity) {
        SystemCodeConfig.opacity = opacity;
    }

    public static int componentSize() {
        return componentSize;
    }

    public static void setComponentSize(int componentSize) {
        SystemCodeConfig.componentSize = componentSize;
    }

    public static int menuBarHeight() {
        return menuBarHeight;
    }

    public static void setMenuBarHeight(int menuBarHeight) {
        SystemCodeConfig.menuBarHeight = menuBarHeight;
    }

    public static int selectHeight() {
        return selectHeight;
    }

    public static void setSelectHeight(int selectHeight) {
        SystemCodeConfig.selectHeight = selectHeight;
    }

    private final static int coreSize = Runtime.getRuntime().availableProcessors();

    static {

        mainThreadNumber = computeThread(2 * taskNumber());
        helpThreadNumber = computeThread(4 * taskNumber());

    }

    public static int helpThreadNumber() {
        return helpThreadNumber;
    }

    public static void setHelpThreadNumber(int helpThreadNumber) {
        SystemCodeConfig.helpThreadNumber = computeThread(helpThreadNumber);
    }

    public static int getCoreSize() {
        return coreSize;
    }

    public static int mainThreadNumber() {
        return mainThreadNumber;
    }


    private static int computeThread(int max) {
        int maxCodeSize = max;
        int num = 0;
        while (num < maxCodeSize) {
            num += coreSize;
        }
        return num;
    }
    private static final Map<String,Object> properties = new ConcurrentHashMap<>();
    public static Map<String,Object> properties(){
        return properties;
    }

    public static void setMainThreadNumber(int mainThreadNumber) {
        SystemCodeConfig.mainThreadNumber = computeThread(mainThreadNumber);
    }

    public static String title() {
        return title;
    }

    public static void setTitle(String title) {
        SystemCodeConfig.title = title;
    }

    public static String defaultMainBackground() {
        return defaultMainBackground;
    }

    public static void setDefaultMainBackground(String defaultMainBackground) {
        defaultMainBackground = defaultMainBackground;
    }

    private SystemCodeConfig() {
    }


    public static int taskNumber() {
        return taskNumber;
    }


    public static void setTaskNumber(int taskNumber) {
        SystemCodeConfig.taskNumber = taskNumber;
    }

    public static String webDriverPath() {
        return webDriverPath;
    }
}
