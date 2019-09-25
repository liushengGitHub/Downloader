package liusheng.downloadCore;

/**
 *
 * 每个视频的 单P下载器
 */
public interface SubPageDownload {
    void download(String vUrl, int quality, String subDir, String fileName) throws Exception;
}
