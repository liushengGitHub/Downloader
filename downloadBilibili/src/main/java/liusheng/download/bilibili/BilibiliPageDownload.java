package liusheng.download.bilibili;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import liusheng.download.bilibili.entity.AdapterParam;
import liusheng.download.bilibili.entity.NewVideoBean;
import liusheng.download.bilibili.entity.OldVideoBean;
import liusheng.download.bilibili.entity.PagesBean;
import liusheng.downloadCore.DownloadList;
import liusheng.downloadCore.SubPageDownload;
import liusheng.downloadCore.entity.AbstractVideoBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DefaultDownloaderController;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.DownloaderController;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

import static liusheng.downloadCore.executor.FailListExecutorService.getTaskQueue;

public class BilibiliPageDownload implements SubPageDownload {
    private DownloadingPaneContainer downloadingPaneContainer;
    private final Logger logger = Logger.getLogger(BilibiliPageDownload.class);
    private final Semaphore semaphore;
    private final PagesBean pagesBean;

    public BilibiliPageDownload(DownloadingPaneContainer downloadingPaneContainer, Semaphore semaphore, PagesBean pagesBean) {
        this.downloadingPaneContainer = downloadingPaneContainer;
        this.semaphore = semaphore;
        this.pagesBean = pagesBean;
    }

    public DownloadingPaneContainer getDownloadingPaneContainer() {
        return downloadingPaneContainer;
    }

    @Override
    public void download(String vUrl, int quality, String fileName, String subDir) throws Exception {
        JFXListView<DownloadItemPaneEntity> listView = downloadingPaneContainer.getListView();
        // 打印日志
        logger.debug("Start Parse " + vUrl);
        // 找到合适的解析器 ,解析对象，有两种类型 NewVideoBean 和 OldVideoBean
        AdapterParam param = new AdapterParam();

        param.setUrl(vUrl);
        param.getMap().put("cid", pagesBean.getVideoData().getCid());
        param.getMap().put("aid", pagesBean.getAid());

        DownloadItemPane downloadItemPane = new DownloadItemPane(new DefaultDownloaderController());
        DownloadItemPaneEntity e1 = new DownloadItemPaneEntity(quality, downloadItemPane);
        downloadItemPane.setEntity(e1);
        // 应该鼬我来产生对象


        // 添加到下载列表
        Platform.runLater(() -> {
            listView.getItems().add(e1);
        });


        FailListExecutorService.commonExecutorService().execute(new FailTask(() -> {

            try {
                // 解析数据
                AbstractVideoBean abstractVideoBean = ensureGetAbstractVideoBean(vUrl, param);
                //name 是文件的名字 videoName是这个视频目录的名字
                setAbstractProperty(downloadingPaneContainer, subDir, listView, abstractVideoBean, fileName, vUrl, downloadItemPane, quality);

                // 重试
                downloadItemPane.getRetry().setOnAction(e -> {
                    int state = downloadItemPane.getLocal().getState();
                    if (state == DownloaderController.EXCEPTION) {
                        if (abstractVideoBean instanceof NewVideoBean) {
                            new NewVideoBeanDownloader(semaphore).download((NewVideoBean) abstractVideoBean);
                        } else if (abstractVideoBean instanceof OldVideoBean) {
                            new OldVideoBeanDownloader(semaphore).download((OldVideoBean) abstractVideoBean);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                });

                //
                e1.setAbstractVideoBean(abstractVideoBean);
                // 放入到任务队列
                getTaskQueue().add(() -> {
                    // 任务加1

                    try {
                        logger.info("开始下载 : " + abstractVideoBean.getRefererUrl());
                        if (abstractVideoBean instanceof NewVideoBean) {
                            new NewVideoBeanDownloader(semaphore).download((NewVideoBean) abstractVideoBean);
                        } else if (abstractVideoBean instanceof OldVideoBean) {
                            new OldVideoBeanDownloader(semaphore).download((OldVideoBean) abstractVideoBean);
                        } else {
                            throw new IllegalArgumentException();
                        }
                        // 下载成功记录数据
                        recordUrl(vUrl);

                        logger.info("下载成功 : " + abstractVideoBean.getRefererUrl());
                    } catch (Throwable throwable) {
                        logger.info("下载成功 : " + abstractVideoBean.getRefererUrl() + " Exception : " + throwable);
                    } finally {
                        // 任务减1
                        FailListExecutorService.getCurrentTaskNumber().getAndDecrement();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }));
    }

    private void recordUrl(String vUrl) {
        DownloadList downloadList = DownloadList.downloadList();
        synchronized (downloadList) {
            downloadList.getUnDownloadList().remove(vUrl);
            downloadList.getDownloadedList().add(vUrl);
        }
    }

    private AbstractVideoBean ensureGetAbstractVideoBean(String vUrl, AdapterParam param) {
        AbstractVideoBean abstractVideoBean = null;
        for (int i = 0; i < 3; i++) {
            try {
                abstractVideoBean = (AbstractVideoBean) new DefaultParserAdapter().handle(param).parse(vUrl);

                return abstractVideoBean;
            } catch (IOException e) {

            }
        }
        return abstractVideoBean;
    }

    private void setAbstractProperty(DownloadingPaneContainer downloadingPaneContainer, String videoName, JFXListView<DownloadItemPaneEntity> listView1, AbstractVideoBean abstractVideoBean, String name, String vUrl, DownloadItemPane downloadItemPane, int quality) {
        abstractVideoBean.setName(StringUtils.fileNameHandle(name));
        abstractVideoBean.setDirFile(new File("video", videoName));
        abstractVideoBean.setRefererUrl(vUrl);
        abstractVideoBean.setSize(new AtomicLong());
        abstractVideoBean.setAllSize(new AtomicLong());
        abstractVideoBean.setDownloadPane(downloadingPaneContainer.getDownloadPane());
        abstractVideoBean.setPane(downloadItemPane);
        abstractVideoBean.setQuality(quality);
        downloadItemPane.setListView(listView1);
    }
}
