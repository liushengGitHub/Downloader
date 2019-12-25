package liusheng.download.kugou;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import liusheng.downloadCore.Downloader;
import liusheng.downloadCore.Error;
import liusheng.downloadCore.RetryDownloader;
import liusheng.downloadCore.entity.AbstractVideoBean;
import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadInterface.DownloaderController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class KugouMp3Downloader implements Downloader<SongEntity> {
    @Override
    public Error download(SongEntity abstractVideoBean) throws IOException {


        FailListExecutorService.commonExecutorServicehelp().execute(() -> {
            Node pane = abstractVideoBean.getPane();
            if (!(pane instanceof DownloadItemPane)) throw new RuntimeException();
            DownloadItemPane itemPane = (DownloadItemPane) pane;
            Label stateLabel = itemPane.getStateLabel();
            Path dirPath = abstractVideoBean.getDirFile().toPath();
            String refererUrl = abstractVideoBean.getRefererUrl();
            String mp3Url = abstractVideoBean.getData().getPlay_url();
            String fileName = abstractVideoBean.getName();
            DownloaderController itemPaneLocal = itemPane.getLocal();
            try {
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                Path dirFile = dirPath.resolve(fileName + ".mp3");
                // 下载视频文件
                Platform.runLater(() -> {
                    itemPane.getPathLabel().setText(dirFile.toString());
                });

                AtomicLong size = abstractVideoBean.getSize();
                AtomicLong allSize = abstractVideoBean.getAllSize();
                AtomicInteger parts = abstractVideoBean.getPartSize();

                 RetryDownloader retryDownloader = new RetryDownloader(itemPaneLocal, size, allSize, parts);
                DownloadEntity downloadEntity = new DownloadEntity(refererUrl, mp3Url, Collections.emptyList(), dirFile, dirPath, 3);

                itemPaneLocal.setState(DownloaderController.EXECUTE);
                Error error = null;
                for (int i = 0; i < 3; i++) {
                    error = retryDownloader.download(downloadEntity);
                    if (Objects.isNull(error.getE())) {
                        break;
                    }
                    retryDownloader.setStart(error.getSum());
                }

                if (Objects.nonNull(error) && Objects.nonNull(error.getE())) {
                    throw new RuntimeException(error.getE());
                }
                if (itemPaneLocal.getState() == DownloaderController.CANCEL) {
                    Platform.runLater(() -> {
                        removeListItem(abstractVideoBean);
                    });
                    return;
                }
                itemPaneLocal.setState(DownloaderController.FINISHED);

                Platform.runLater(() -> {
                    stateLabel.setText("下载完成");
                    // 移除下载项
                    removeListItem(abstractVideoBean);
                });

            } catch (Throwable e) {
                itemPaneLocal.setState(DownloaderController.EXCEPTION);
                Platform.runLater(() -> {
                    stateLabel.setText("下载失败.. 点击重试");
                });
                throw new RuntimeException(e);
            } finally {
            }
        });


        // 其中一个下载失败,则两个重新下载,因这种处理方式简单
        // 限流
        // 到达这里说明下载完成,提示一下


        return null;
    }

    private void removeListItem(AbstractVideoBean newVideoBean) {
        DownloadItemPane itemPane = (DownloadItemPane) newVideoBean.getPane();
        JFXListView<DownloadItemPaneEntity> listView = itemPane.getListView();
        ObservableList<DownloadItemPaneEntity> items = listView.getItems();
        int i = items.indexOf(itemPane.getEntity());
        newVideoBean.getDownloadPane().getDownloadedPane().getDownloadedPaneContainer().getListView().getItems().add(items.get(i));
        items.remove(i);
    }
}
