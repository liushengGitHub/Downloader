package liusheng.downloadCore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DownloadList {
    private final List<String> unDownloadList = new LinkedList<>();
    private final List<String> DownloadedList = new LinkedList<>();

    public DownloadList() {
        // 加载上次没有下载完的
        Path path = Paths.get("download/unDownloadUrls");
        try {
            Files.lines(path).forEach(url -> {
                unDownloadList.add(url);
            });

        } catch (IOException e) {

        }
    }

    private static DownloadList downloadList = new DownloadList();

    public static DownloadList downloadList() {
        return downloadList;
    }

    public List<String> getUnDownloadList() {
        return unDownloadList;
    }

    public List<String> getDownloadedList() {
        return DownloadedList;
    }
}
