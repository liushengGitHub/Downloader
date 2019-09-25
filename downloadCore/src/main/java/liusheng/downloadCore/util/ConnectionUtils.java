package liusheng.downloadCore.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtils {

    /**
     *
     * 默认请求的大小是16M
     */
    public static final int BYTES = 16 * 1024 * 1024;

    public static Connection getConnection(String url) {
        return getDoConnection(url, 3);
    }

    private static Connection getDoConnection(String url, int n) {
        if (n == 0) return null;
        try {
            return Jsoup.connect(url)
                    .ignoreContentType(true)
                    .maxBodySize(BYTES)
                    .timeout(60000);
        } catch (Exception e) {
            return getDoConnection(url, n - 1);
        }
    }

    public static HttpURLConnection getNativeConnection(String videoUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(videoUrl).openConnection();

        connection.setConnectTimeout(60000);
        connection.setReadTimeout(60000);

        return connection;
    }
}
