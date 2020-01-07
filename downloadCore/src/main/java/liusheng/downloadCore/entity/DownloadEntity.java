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

    /**
     *  来源地址
     */
    private String refererUrl;
    /**
     * 下载的url
     */
    private String url;
    /**
     * 备用的urls
     */
    private List<String> bUrls;

    /**
     * 下载文件路径
     */
    private Path filePath;
    /**
     * 下载文件的目录
     */
    private Path dirFile;
    /**
     * 重试次数
     */
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
