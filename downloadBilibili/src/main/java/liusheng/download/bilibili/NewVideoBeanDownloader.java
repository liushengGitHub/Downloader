package liusheng.download.bilibili;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import liusheng.download.bilibili.entity.DashBean;
import liusheng.download.bilibili.entity.NewVideoBean;
import liusheng.download.bilibili.transefer.MergeAudioAndVideoFile;
import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.Downloader;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.RetryDownloader;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadInterface.DownloaderController;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import liusheng.downloadCore.Error;

public class NewVideoBeanDownloader implements Downloader<NewVideoBean> {
    private final Logger logger = Logger.getLogger(NewVideoBeanDownloader.class);
    private final Semaphore semaphore;

    public NewVideoBeanDownloader(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    static class StateCountDownLatch extends CountDownLatch {
        volatile boolean error = false;

        public StateCountDownLatch(int count) {
            super(count);
        }
    }

    @Override
    public Error download(NewVideoBean newVideoBean) {
        Optional.ofNullable(newVideoBean).map(NewVideoBean::getData)
                .map(NewVideoBean.DataBean::getDash)
                .ifPresent(dashBean -> {
                    Path flvPath = null;
                    Path mp3Path = null;
                    Node pane = newVideoBean.getPane();
                    if (!(pane instanceof DownloadItemPane)) throw new IllegalArgumentException();

                    DownloadItemPane itemPane = (DownloadItemPane) pane;
                    DownloaderController itemPaneLocal = itemPane.getLocal();
                    try {
                        List<DashBean.AudioBean> audioBeanList = dashBean.getAudio();

                        List<DashBean.VideoBean> videoBeanList = dashBean.getVideo();

                        if (audioBeanList == null || audioBeanList.size() == 0) {
                            return;
                        }

                        if (videoBeanList == null || videoBeanList.size() == 0) {
                            return;
                        }
                        int quality = newVideoBean.getQuality();
                        String fileName = newVideoBean.getName();
                        String refererUrl = newVideoBean.getUrl();
                        File dirFile = newVideoBean.getDirFile();


                        DashBean.VideoBean videoBean = null;
                        if (quality < 0) {
                            videoBean = videoBeanList.stream().max(Comparator.comparing(DashBean.VideoBean::getId)).get();
                        } else {
                            videoBean = videoBeanList.stream().filter(video -> video.getId() == quality).findFirst().orElse(null);
                        }

                        if (Objects.isNull(videoBean)) {
                            videoBean = videoBeanList.stream().max(Comparator.comparing(DashBean.VideoBean::getId)).get();
                        }
                        List<String> vbUrls = videoBean.getBackupUrl();
                        String vbUrl = videoBean.getBaseUrl();

                        Path dirPath = dirFile.toPath();
                        if (!Files.exists(dirPath)) {
                            Files.createDirectories(dirPath);
                        }
                        flvPath = dirPath.resolve(fileName + ".flv.temp");
                        mp3Path = dirPath.resolve(fileName + ".mp3.temp");
                        // 下载视频文件
                        Path flvPathTemp = flvPath;
                        Platform.runLater(() -> {
                            itemPane.getPathLabel().setText(dirPath.resolve(fileName + ".flv").toString());
                        });

                        AtomicLong size = newVideoBean.getSize();
                        AtomicLong allSize = newVideoBean.getAllSize();
                        AtomicInteger parts = newVideoBean.getPartSize();
                        StateCountDownLatch state = new StateCountDownLatch(1);
                        FailListExecutorService.commonExecutorServicehelp().execute(() -> {
                            try {
                                RetryDownloader retryDownloader = new RetryDownloader(itemPaneLocal, size, allSize, parts);
                                DownloadEntity downloadEntity = new DownloadEntity(refererUrl, vbUrl, vbUrls, flvPathTemp, dirPath, 3);

                                itemPaneLocal.setState(DownloaderController.EXECUTE);
                                Error error = null;
                                for (int i = 0; i < 3; i++) {
                                    error = retryDownloader.download(downloadEntity);
                                    if (Objects.isNull(error.getE())) {
                                        return;
                                    }
                                    retryDownloader.setStart(error.getSum());
                                }
                                // 失败

                                throw new RuntimeException(error.getE());
                            } catch (Throwable e) {
                                state.error = true;
                                throw new RuntimeException(e);
                            } finally {
                                state.countDown();
                            }
                        });
                        // 下载音频文件
                        String aBUrl = audioBeanList.get(0).getBaseUrl();
                        List<String> aBUrls = audioBeanList.get(0).getBackupUrl();
                        RetryDownloader retryDownloader = new RetryDownloader(itemPaneLocal, size, allSize, parts);
                        DownloadEntity downloadEntity = new DownloadEntity(refererUrl, aBUrl, aBUrls, mp3Path, dirPath, 3);
                        retryDownloader.download(downloadEntity);
                        // 等待 异步线程的完成
                        state.await();
                        if (itemPaneLocal.getState() == DownloaderController.CANCEL) {
                            Platform.runLater(() -> {
                                removeListItem(newVideoBean);
                            });
                            throw new RuntimeException();
                        }
                        // 其中一个下载失败,则两个重新下载,因这种处理方式简单
                        // 限流
                        // 到达这里说明下载完成,提示一下
                        Label stateLabel = itemPane.getStateLabel();
                        if (state.error) {

                            itemPaneLocal.setState(DownloaderController.EXCEPTION);
                            Platform.runLater(() -> {
                                stateLabel.setText("下载失败.. 点击重试");
                            });
                            throw new RuntimeException();
                        } else {
                            itemPaneLocal.setState(DownloaderController.FINISHED);
                        }


                        Platform.runLater(() -> {
                            stateLabel.setText("下载完成.. 正在合并");
                        });

                        try {
                            semaphore.acquire();
                            new MergeAudioAndVideoFile(flvPath, mp3Path, fileName).run();
                        } catch (Exception e) {
                            Platform.runLater(() -> {
                                stateLabel.setText("合并失败..点击重试");
                            });

                        } finally {
                            // 必须放到finally 里面
                            semaphore.release();
                        }

                        Platform.runLater(() -> {
                            stateLabel.setText("合成完毕...");
                            removeListItem(newVideoBean);
                        });


                    } catch (Throwable throwable) {

                        // 取消音频的下载,并删除 ,如果下载完成或者下载失败则无效
                        // 失败则删除文件 和抛出异常
                        try {
                            if (Objects.nonNull(flvPath) && Files.exists(flvPath))
                                Files.delete(flvPath);
                            if (Objects.nonNull(mp3Path) && Files.exists(mp3Path))
                                Files.delete(mp3Path);
                        } catch (IOException e) {
                            logger.debug("文件删除失败");
                        }

                    }
                });
        return null;
    }

    private void removeListItem(NewVideoBean newVideoBean) {
        DownloadItemPane itemPane = (DownloadItemPane) newVideoBean.getPane();
        JFXListView<DownloadItemPaneEntity> listView = itemPane.getListView();
        ObservableList<DownloadItemPaneEntity> items = listView.getItems();
        int i = items.indexOf(itemPane.getEntity());
        newVideoBean.getDownloadPane().getDownloadedPane().getDownloadedPaneContainer().getListView().getItems().add(items.get(i));
        items.remove(i);
    }
}
