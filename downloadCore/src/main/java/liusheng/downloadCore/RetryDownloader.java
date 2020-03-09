package liusheng.downloadCore;

import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.DownloaderController;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RetryDownloader implements Downloader<DownloadEntity> {


    private final Logger logger = Logger.getLogger(RetryDownloader.class);
    private DownloaderController itemPaneLocal;
    private AtomicLong size;
    private AtomicLong allSize;
    private long start;
    private long length = -1;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    private AtomicInteger parts;

    /*

     * */

    public AtomicLong getSize() {
        return size;
    }

    public AtomicLong getAllSize() {
        return allSize;
    }

    private boolean first = true;

    public RetryDownloader(DownloaderController itemPaneLocal, AtomicLong size, AtomicLong allSize, AtomicInteger parts) {

        this.itemPaneLocal = itemPaneLocal;
        this.size = size;
        this.allSize = allSize;
        this.parts = parts;
    }

    @Override
    public Error download(DownloadEntity downloadEntity) throws IOException {
        int retry = downloadEntity.getRetry();
        String url = downloadEntity.getUrl();
        Path filePath = downloadEntity.getFilePath();
        String refererUrl = downloadEntity.getRefererUrl();
        // 下载
        Error error = retryDownload(retry, url, filePath, refererUrl);
        return error;
    }


    private Error retryDownload(int retry, String url, Path filePath, String refererUrl) throws IOException {

        Error error = null;
        try(OutputStream outputStream = Files.newOutputStream(filePath)) {
            for (int i = 0; i < retry; i++) {
                error = downloadFile(refererUrl, url, outputStream);
                if (Objects.isNull(error.e) || error.e instanceof InterruptedException) {
                    return error;
                }
                // 上次的位置开始下载
                start = error.sum;
                parts.getAndDecrement();
            }
        }
        return error;

    }

    private Error downloadFile(String url, String videoUrl, OutputStream outputStream) throws IOException {
        HttpURLConnection connection = ConnectionUtils.getNativeConnection(videoUrl);

        connection.setRequestMethod("GET");
        connection.addRequestProperty("Referer", url);
        logger.debug(videoUrl + "  Download Started ");
        if (!first) {
            connection.addRequestProperty("Range", "bytes=" + start + "-" + (length - 1));
        }

        long len = connection.getContentLengthLong();
        parts.getAndIncrement();

        // 第一次进入,才增加长度,否正不增加

        if (len <= 0) throw new IllegalStateException();
        InputStream inputStream = connection.getInputStream();
        long sum = start;

        if (!first &&len == this.length) {
            inputStream.skip(sum);
        }
        if (first) {
            allSize.getAndAdd(len);
            first = false;
            this.length = len;
            logger.info(url + "=" + len);
        }
        try  {

            byte[] bytes = new byte[10240];
            while (sum < this.length) {
                int state = itemPaneLocal.getState();
                if (DownloaderController.CANCEL == state || Thread.currentThread().isInterrupted() || state == DownloaderController.EXCEPTION) {
                    // 取消的删除 关闭流
                    outputStream.close();
                    break;
                }
                if (DownloaderController.PAUSE == state) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        outputStream.close();
                        return  new Error(sum,e);
                    }
                    continue;
                }
                int length = inputStream.read(bytes);
                if (length == -1 || length == 0) {

                    try {
                        // 关闭失败也没有关系
                        inputStream.close();
                        connection.disconnect();
                    } catch (Exception e) {

                    }
                    connection = ConnectionUtils.getNativeConnection(videoUrl);

                    connection.setRequestMethod("GET");
                    connection.addRequestProperty("Referer", url);
                    connection.addRequestProperty("Range", "bytes=" + sum + "-" + (this.length - 1));

                    inputStream = connection.getInputStream();

                    if (connection.getContentLengthLong() == this.length) {
                        inputStream.skip(sum);
                    }
                    continue;
                }

                outputStream.write(bytes, 0, length);
               /* start += length;*/
                sum += length;
                size.getAndAdd(length);
            }
            return new Error(sum, null);
        } catch (IOException e) {
            logger.debug(videoUrl + "  Download Error ");
            return new Error(sum, e);

        } finally {
            inputStream.close();
        }
    }


}
