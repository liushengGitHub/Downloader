package liusheng.download.manhuadui.donwload;


import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import liusheng.download.manhuadui.bean.Image;
import liusheng.download.manhuadui.parse.ChapterImageParser;
import liusheng.downloadCore.Downloader;
import liusheng.downloadCore.Error;
import liusheng.downloadCore.RetryDownloader;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.LimitRunnable;
import liusheng.downloadCore.pane.DefaultDownloaderController;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.AbstractBeanUtil;
import liusheng.downloadCore.util.StatusUtil;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.DownloaderController;
import liusheng.downloadInterface.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static liusheng.downloadCore.executor.ListExecutorService.getTaskQueue;

public class ChapterDownloader implements Downloader<String> {
    private final String dir;
    private Parser<String, List<Image>> parser = new ChapterImageParser();

    public ChapterDownloader(String dir) {
        this.dir = dir;
    }

    public Error download(String url) {

        String name = StringUtils.urlToFileName(url);
        int index = name.lastIndexOf(".");
        if (index != -1) name = name.substring(0, index);

        File dirFile = new File(dir, name);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            List<Image> images = parser.parse(url);

            IntStream.rangeClosed(1, images.size()).parallel().forEach(i -> {
                String refer = url + "?p=" + i;
                DefaultDownloaderController local = new DefaultDownloaderController();
                DownloadingPaneContainer paneContainer = DownloadingPaneContainer.getMe();
                JFXListView<DownloadItemPaneEntity> listView = paneContainer.getListView();
                DownloadItemPane downloadItemPane = new DownloadItemPane(local);

                Image image = images.get(i - 1);
                DownloadItemPaneEntity e1 = new DownloadItemPaneEntity(-1, downloadItemPane);

                AbstractBeanUtil.setAbstractProperty(paneContainer, dirFile, listView, image, image.getName(), refer, downloadItemPane, -1, e1);

                Platform.runLater(() -> {
                    listView.getItems().add(e1);
                });

                Path dirPath = dirFile.toPath();
                Path filePath = dirPath.resolve(image.getName());

                Platform.runLater(() -> {
                    downloadItemPane.getPathLabel().setText(filePath.toString());
                });

                downloadItemPane.getRetry().setOnAction(e -> {
                    // 加入到任务队列
                    if (downloadItemPane.getLocal().getState() == DownloaderController.EXCEPTION) {
                        StatusUtil.retry(image,downloadItemPane);
                        addTaskQueue(refer, local, downloadItemPane, image, dirPath, filePath);
                    }
                });

                local.setState(DownloaderController.EXECUTE);
                // 加入到任务队列
                addTaskQueue(refer, local, downloadItemPane, image, dirPath, filePath);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addTaskQueue(String refer, DefaultDownloaderController local, DownloadItemPane downloadItemPane, Image image, Path dirPath, Path filePath) {
        getTaskQueue().add(new LimitRunnable(image, () -> {
            try {
                StatusUtil.download(downloadItemPane);

                Error error = downloadImage(refer, downloadItemPane, image, dirPath, filePath);

                if (Objects.nonNull(error.getE())) {
                    throw new RuntimeException(error.getE());
                }

                // ch冲列表项 移除项
                local.setState(DownloaderController.FINISHED);

                Platform.runLater(() -> {
                    downloadItemPane.getStateLabel().setText("下载完成");
                    // 移除下载项
                    removeListItem(image);
                });
                downloadItemPane.getLocal().setState(DownloaderController.FINISHED);
            } catch (Throwable e) {
                downloadItemPane.getLocal().setState(DownloaderController.EXCEPTION);
                throw new RuntimeException(e);
            }
        }));
    }

    private Error downloadImage(String refer, DownloadItemPane downloadItemPane, Image image, Path dirPath, Path filePath) throws IOException {
        return new RetryDownloader(downloadItemPane.getLocal(), image.getSize(), image.getAllSize(), new AtomicInteger())
                .download(new DownloadEntity(refer, image.getUrl(), Collections.emptyList(),
                        filePath, dirPath, 3));
    }

    private void removeListItem(AbstractDataBean newVideoBean) {
        DownloadItemPane itemPane = (DownloadItemPane) newVideoBean.getPane();
        JFXListView<DownloadItemPaneEntity> listView = itemPane.getListView();
        ObservableList<DownloadItemPaneEntity> items = listView.getItems();
        int i = items.indexOf(itemPane.getEntity());
        newVideoBean.getDownloadPane().getDownloadedPane().getDownloadedPaneContainer().getListView().getItems().add(items.get(i));
        items.remove(i);
    }

}
