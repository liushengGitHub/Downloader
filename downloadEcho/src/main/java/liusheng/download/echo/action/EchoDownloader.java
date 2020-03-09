package liusheng.download.echo.action;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import liusheng.downloadCore.Downloader;
import liusheng.downloadCore.Error;
import liusheng.downloadCore.RetryDownloader;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.executor.LimitRunnable;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.util.StatusUtil;
import liusheng.downloadInterface.DownloaderController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static liusheng.downloadCore.util.DownloadPaneUtil.removeListItem;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 31
 * 用户名: LiuSheng
 */

public class EchoDownloader implements Downloader<AbstractDataBean> {
    @Override
    public Error download(AbstractDataBean abstractDataBean) throws IOException {
        Node pane = abstractDataBean.getPane();
        if (!(pane instanceof DownloadItemPane)) throw new RuntimeException();
        DownloadItemPane itemPane = (DownloadItemPane) pane;
        Label stateLabel = itemPane.getStateLabel();
        Path dirPath = abstractDataBean.getDirFile().toPath();
        String refererUrl = abstractDataBean.getRefererUrl();
        String fileName = abstractDataBean.getName();
        DownloaderController itemPaneLocal = itemPane.getLocal();
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path filePath = dirPath.resolve(fileName + ".mp3");
        // 下载视频文件
        Platform.runLater(() -> {
            itemPane.getPathLabel().setText(filePath.toString());
        });

        AtomicLong size = abstractDataBean.getSize();
        AtomicLong allSize = abstractDataBean.getAllSize();
        AtomicInteger parts = abstractDataBean.getPartSize();

        RetryDownloader retryDownloader = new RetryDownloader(itemPaneLocal, size, allSize, parts);
        DownloadEntity downloadEntity = new DownloadEntity(refererUrl, refererUrl, Collections.emptyList(), filePath, dirPath, 3);

        itemPane.getRetry().setOnAction((e)->{
            int state = itemPane.getLocal().getState();
            if (state == DownloaderController.EXCEPTION) {
                StatusUtil.retry(abstractDataBean,itemPane);
                addTaskQueue(abstractDataBean, itemPane, stateLabel, itemPaneLocal, retryDownloader, downloadEntity);
            }
        });
        // 添加到任务队列
        addTaskQueue(abstractDataBean, itemPane, stateLabel, itemPaneLocal, retryDownloader, downloadEntity);

        return null;
    }

    private void addTaskQueue(AbstractDataBean abstractDataBean, DownloadItemPane itemPane, Label stateLabel, DownloaderController itemPaneLocal, RetryDownloader retryDownloader, DownloadEntity downloadEntity) {
        ListExecutorService.getTaskQueue().add(new LimitRunnable(abstractDataBean, () -> {
            StatusUtil.download(itemPane);
            realDownload(abstractDataBean, stateLabel, itemPaneLocal, retryDownloader, downloadEntity);
        }));
    }

    private void realDownload(AbstractDataBean abstractDataBean, Label stateLabel, DownloaderController itemPaneLocal, RetryDownloader retryDownloader, DownloadEntity downloadEntity) {
        try {
            Error error = retryDownloader.download(downloadEntity);
            if (Objects.nonNull(error) && Objects.nonNull(error.getE())) {
                throw new RuntimeException(error.getE());
            }
            if (itemPaneLocal.getState() == DownloaderController.CANCEL) {
                Platform.runLater(() -> {
                    removeListItem(abstractDataBean);
                });
                return;
            }
            itemPaneLocal.setState(DownloaderController.FINISHED);

            Platform.runLater(() -> {
                stateLabel.setText("下载完成");
                // 移除下载项
                removeListItem(abstractDataBean);
            });
        } catch (IOException e) {
            itemPaneLocal.setState(DownloaderController.EXCEPTION);
            throw new RuntimeException(e);
        }
    }


}
