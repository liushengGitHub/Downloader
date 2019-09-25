package liusheng.downloadCore.entity;

import java.nio.file.Path;
import java.util.List;

public class DownloadEntity {
    public String getRefererUrl() {
        return refererUrl;
    }

    public void setRefererUrl(String refererUrl) {
        this.refererUrl = refererUrl;
    }

    private String refererUrl;
    private String url;
    private List<String> bUrls;
    private Path filePath;
    private Path dirFile;
    private int retry;
    public DownloadEntity(String refererUrl, String url, List<String>  bUrls, Path filePath, Path dirFile, int retry) {
        this.refererUrl = refererUrl;
        this.url = url;
        this.bUrls = bUrls;
        this.filePath = filePath;
        this.dirFile = dirFile;
        this.retry = retry;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getbUrls() {
        return bUrls;
    }

    public void setbUrls(List<String> bUrls) {
        this.bUrls = bUrls;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDirFile() {
        return dirFile;
    }

    public void setDirFile(Path dirFile) {
        this.dirFile = dirFile;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}
