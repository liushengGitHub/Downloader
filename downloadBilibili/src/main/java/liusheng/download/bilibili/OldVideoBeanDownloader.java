package liusheng.download.bilibili;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import liusheng.download.bilibili.entity.OldVideoBean;
import liusheng.download.bilibili.transefer.MergeFile;
import liusheng.downloadCore.entity.DownloadEntity;
import liusheng.downloadCore.Downloader;
import liusheng.downloadCore.Error;
import liusheng.downloadCore.RetryDownloader;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadInterface.DownloaderController;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static liusheng.downloadCore.util.DownloadPaneUtil.removeListItem;

public class OldVideoBeanDownloader implements Downloader<OldVideoBean> {
    private final Logger logger = Logger.getLogger(NewVideoBeanDownloader.class);
    private final Semaphore semaphore;
    private boolean merge = true;

    public OldVideoBeanDownloader(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public Error download(OldVideoBean oldVideoBean) {
        Optional.ofNullable(oldVideoBean.getData())
                .map(OldVideoBean.DataBean::getDurl)
                .ifPresent(durlBeans -> {
                    List<String> paths = new LinkedList<>();
                    String name = oldVideoBean.getName();
                    Path dirPath = oldVideoBean.getDirFile().toPath();
                    Node pane = oldVideoBean.getPane();
                    if (!(pane instanceof DownloadItemPane)) throw new RuntimeException();
                    DownloadItemPane itemPane = (DownloadItemPane) pane;
                    DownloaderController itemPaneLocal = itemPane.getLocal();
                    boolean b = durlBeans.size() > 1;
                    try {

                        if (durlBeans.isEmpty()) return;

                        String refererUrl = oldVideoBean.getRefererUrl();

                        AtomicLong size = oldVideoBean.getSize();
                        AtomicLong allSize = oldVideoBean.getAllSize();
                        AtomicInteger parts = oldVideoBean.getPartSize();
                        oldVideoBean.setParts(durlBeans.size());


                        itemPaneLocal.setState(DownloaderController.EXECUTE);

                        if (!Files.exists(dirPath)) {
                            Files.createDirectories(dirPath);
                        }
                        Label stateLabel = itemPane.getStateLabel();
                        // 下载这个视频的所有分段 (多线程) ,是由bug 的  如果在
                        // 非启动线抛出的异常,会捕获不到的
                        // 用于记录下载成功的数量
                        AtomicInteger successNumber = new AtomicInteger();
                        Path finalFilePath = dirPath.resolve(name + ".flv");
                        oldVideoBean.setFilePath(finalFilePath);
                        //                        Path finalFilePath = dirPath.resolve(name + ".flv");
                        Path finalTxtPath = dirPath.resolve(name + ".txt");
                        Platform.runLater(() -> {
                            itemPane.getPathLabel().setText(finalFilePath.toString());
                        });

                        durlBeans.stream().forEach(durlBean -> {
                            int order = durlBean.getOrder();
                            String videoUrl = durlBean.getUrl();

                            List<String> backup_url = durlBean.getBackup_url() == null ? Collections.emptyList() : (List<String>) durlBean.getBackup_url();

                            Path filePath = dirPath.resolve((b ? order + "_" : "") + name + ".flv");


                            // 加入合并的路径,失败的话就删除
                            paths.add(filePath.toString());

                            try {

                                RetryDownloader retryDownloader = new RetryDownloader(itemPaneLocal, size, allSize, parts);

                                Error error = retryDownloader.download(new DownloadEntity(refererUrl, videoUrl, backup_url, filePath, dirPath, 3));


                                // 用户取消了
                                if (itemPaneLocal.getState() == DownloaderController.CANCEL) {
                                    Platform.runLater(() -> {
                                        removeListItem(oldVideoBean);
                                    });

                                    return;
                                }

                                // 下载成功
                                if (Objects.isNull(error.getE())) {
                                    successNumber.getAndIncrement();
                                } else {

                                    itemPaneLocal.setState(DownloaderController.EXCEPTION);
                                }

                            } catch (Throwable e) {

                                itemPaneLocal.setState(DownloaderController.EXCEPTION);
                                Platform.runLater(() -> {
                                    stateLabel.setText("下载失败");
                                });
                            }

                        });

                        if (successNumber.get() < oldVideoBean.getParts()) {
                            throw new RuntimeException("下载失败");
                        }
                        // 下载成功
                        itemPaneLocal.setState(DownloaderController.FINISHED);

                        Platform.runLater(() -> {
                            stateLabel.setText("下载完成,正在合并");
                        });

                        logger.info(refererUrl + "  Download Completed ");
                        if (b) {
                            try {
                                //限流,防止OOM
                                semaphore.acquire();
                                new MergeFile(paths, finalTxtPath.toString(), finalFilePath.toString(), semaphore).run();
                            } catch (Exception e) {
                                logger.debug("合并失败",e);
                                merge = false;
                            } finally {
                                semaphore.release();
                            }
                        }

                        if (merge) {
                            Platform.runLater(() -> {
                                stateLabel.setText("合并成功");
                                removeListItem(oldVideoBean);
                            });
                        } else {
                            Platform.runLater(() -> {
                                stateLabel.setText("合并失败");
                            });
                            throw  new RuntimeException();
                        }

                        itemPaneLocal.setState(DownloaderController.FINISHED);

                    } catch (Throwable throwable) {
                        itemPaneLocal.setState(DownloaderController.EXCEPTION);
                        throw new RuntimeException(throwable);
                    } finally {
                        if (!b || !merge) {
                            return;
                        }
                        // 删除所有临时文件
                        int[] fails = new int[1];
                        try {
                            Path pathTxt = dirPath.resolve(name + ".txt");
                            if (Files.exists(pathTxt)) {
                                Files.delete(pathTxt);
                            }
                            // 不应该使用forEach 效率会降低
                            paths.forEach(pathStr -> {
                                Path path = Paths.get(pathStr);
                                if (Files.exists(path)) {
                                    try {
                                        Files.delete(path);
                                    } catch (IOException e) {
                                        fails[0]++;
                                    }
                                }
                            });
                        } catch (Exception e) {
                            fails[0]++;
                        }
                        if (fails[0] > 0) {
                            logger.debug("删除文件失败");
                        }
                    }
                });
        return null;
    }


}
