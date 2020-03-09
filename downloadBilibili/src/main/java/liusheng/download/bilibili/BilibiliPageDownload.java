package liusheng.download.bilibili;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import liusheng.download.bilibili.entity.AdapterParam;
import liusheng.download.bilibili.entity.NewVideoBean;
import liusheng.download.bilibili.entity.OldVideoBean;
import liusheng.download.bilibili.entity.PagesBean;
import liusheng.downloadCore.DefaultRunnableInfo;
import liusheng.downloadCore.DownloadList;
import liusheng.downloadCore.RunnableInfo;
import liusheng.downloadCore.SubPageDownload;
import liusheng.downloadCore.config.PathConstants;
import liusheng.downloadCore.config.SystemCodeConfig;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.LimitRunnable;
import liusheng.downloadCore.pane.DefaultDownloaderController;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.AbstractBeanUtil;
import liusheng.downloadCore.util.StatusUtil;
import liusheng.downloadInterface.DownloaderController;
import liusheng.downloadInterface.SystemConfigLoader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

import static liusheng.downloadCore.executor.ListExecutorService.getTaskQueue;

public class BilibiliPageDownload implements SubPageDownload {
    private DownloadingPaneContainer downloadingPaneContainer;
    private final Logger logger = Logger.getLogger(BilibiliPageDownload.class);
    private static final Semaphore semaphore  = (Semaphore) SystemCodeConfig.properties().get("semaphore");;
    private final PagesBean pagesBean;

    public BilibiliPageDownload(DownloadingPaneContainer downloadingPaneContainer, PagesBean pagesBean) {
        this.downloadingPaneContainer = downloadingPaneContainer;

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

        RunnableInfo runnableInfo = new DefaultRunnableInfo(downloadItemPane,vUrl);
        // 添加到下载列表
        Platform.runLater(() -> {
            listView.getItems().add(e1);
        });
        File subDirFile = new File(SystemConfigLoader.getPropertyOrDefault("video","video"), subDir);
        downloadItemPane.getRetry().setOnAction(e -> {
            int state = downloadItemPane.getLocal().getState();
            if (state == DownloaderController.EXCEPTION) {
                StatusUtil.retry(null,downloadItemPane);
                // 放入到任务队列
                getTaskQueue().add(new LimitRunnable(runnableInfo, () -> {
                    StatusUtil.download(downloadItemPane);
                    getVieoBeanAndDownload(vUrl, quality, fileName, subDirFile, listView, param, downloadItemPane, e1);
                }));
            }
        });
        Platform.runLater(() -> {
            downloadItemPane.getPathLabel().setText(subDirFile.toPath().resolve(fileName + ".flv").toString());
        });

        // 放入到任务队列
        getTaskQueue().add(new LimitRunnable(runnableInfo, () -> {

            getVieoBeanAndDownload(vUrl, quality, fileName, subDirFile, listView, param, downloadItemPane, e1);

        }));

    }

    private void getVieoBeanAndDownload(String vUrl, int quality, String fileName, File subDir, JFXListView<DownloadItemPaneEntity> listView, AdapterParam param, DownloadItemPane downloadItemPane, DownloadItemPaneEntity e1) {
        try {

            downloadItemPane.getLocal().setState(DownloaderController.EXECUTE);
            // 解析数据
            AbstractDataBean abstractDataBean = ensureGetAbstractVideoBean(vUrl, param);
            if (Objects.isNull(abstractDataBean)) {
                Platform.runLater(()->{
                    downloadItemPane.getStateLabel().setText("获取数据失败");
                });
                return;
            }
            //name 是文件的名字 videoName是这个视频目录的名字
            AbstractBeanUtil.setAbstractProperty(downloadingPaneContainer, subDir, listView, abstractDataBean, fileName, vUrl, downloadItemPane, quality, e1);
            // 重试
            if (abstractDataBean instanceof NewVideoBean) {
                new NewVideoBeanDownloader(semaphore).download((NewVideoBean) abstractDataBean);
            } else if (abstractDataBean instanceof OldVideoBean) {
                new OldVideoBeanDownloader(semaphore).download((OldVideoBean) abstractDataBean);
            } else {
                throw new IllegalArgumentException();
            }
            // 下载成功记录数据
            recordUrl(vUrl);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    private void recordUrl(String vUrl) {
        DownloadList downloadList = DownloadList.downloadList();
        synchronized (downloadList) {
            downloadList.getUnDownloadList().remove(vUrl);
            downloadList.getDownloadedList().add(vUrl);
        }
    }

    private AbstractDataBean ensureGetAbstractVideoBean(String vUrl, AdapterParam param) {
        AbstractDataBean abstractDataBean = null;
        for (int i = 0; i < 3; i++) {
            try {
                abstractDataBean = (AbstractDataBean) new DefaultParserAdapter().handle(param).parse(vUrl);

                return abstractDataBean;
            } catch (IOException e) {

            }
        }
        return abstractDataBean;
    }


}
