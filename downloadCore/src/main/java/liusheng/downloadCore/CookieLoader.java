package liusheng.downloadCore;

import cn.hutool.core.collection.CollectionUtil;
import liusheng.downloadCore.config.PathConstants;
import liusheng.downloadCore.config.SystemCodeConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 年: 2019  月: 12 日: 31 小时: 11 分钟: 27
 * 用户名: LiuSheng
 */

public class CookieLoader {
    private CookieLoader() {
    }

    private static Map<String, String> cookies = null;

    public static void main(String[] args) {
        cookies();
    }
    public static Map<String, String> cookies() {
        if (Objects.isNull(cookies)) {
            try {
                cookies = (Map<String, String>) getFromProperties();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (CollectionUtil.isEmpty(cookies)) {
            try {
                cookies = getFromSelenium();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (CollectionUtil.isEmpty(cookies)) {
            return Collections.emptyMap();
        }

        try {
            storeCookiesToFile(cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cookies;
    }

    private static void storeCookiesToFile(Map<String, String> cookies) throws IOException {
        Properties properties = new Properties();
        Path configPath = Paths.get(PathConstants.CONFIG);

        if (!Files.exists(configPath)) {
            Files.createDirectories(configPath);
        }
        properties.store(Files.newOutputStream(configPath.resolve("config")),".");
    }

    private static Map<String, String> getFromSelenium() throws Exception {
        String webDriverPath = SystemCodeConfig.webDriverPath();
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, webDriverPath);
        WebDriver webDriver = new InternetExplorerDriver();

        webDriver.manage().timeouts().pageLoadTimeout(15,TimeUnit.SECONDS);
        webDriver.get("https://passport.bilibili.com/login");
        checkChange(webDriver);

        return doGetCookies(webDriver);
    }

    private static Map<String, String> doGetCookies(WebDriver webDriver) {

        HashMap<String, String> map = webDriver.manage().getCookies()
                .stream()
                .collect(HashMap::new, (hashMap, cookie) -> {
                    hashMap.put(cookie.getName(), cookie.getValue());
                }, Map::putAll);
        try{
            webDriver.close();
            webDriver.quit();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return map;
    }

    private static void checkChange(WebDriver webDriver) throws InterruptedException {
        String originUrl = webDriver.getCurrentUrl();
        while (true) {
            String currentUrl = webDriver.getCurrentUrl();
            if (!currentUrl.equals(originUrl)) {
                return;
            }
            TimeUnit.SECONDS.sleep(1);
            originUrl = currentUrl;
        }
    }

    private static Map<?, ?> getFromProperties() throws IOException {
        Properties properties = new Properties();

        Path cookiesPath = Paths.get("config/cookies.properties");

        if (Files.exists(Paths.get("config/cookies.properties"))) {
            properties.load(Files.newInputStream(cookiesPath));
        }
        return properties;
    }
}
