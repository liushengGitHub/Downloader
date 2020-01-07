package liusheng.download.echo.action;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.pane.DefaultDownloaderController;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadInterface.DownloaderController;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 20
 * 用户名: LiuSheng
 */

public class EchoDownloadAction extends AbstractDownloadAction {

    public EchoDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {
        super(href, downloadingPaneContainer);
    }

    @Override
    protected void asynHandle(ActionEvent event) {
        try {
            JFXListView<DownloadItemPaneEntity> listView = downloadingPaneContainer.getListView();

            AbstractDataBean abstractDataBean = new AbstractDataBean() {
                @Override
                public int getParts() {
                    return 0;
                }
            };

            DownloadItemPane downloadItemPane = new DownloadItemPane(new DefaultDownloaderController());
            DownloadItemPaneEntity e1 = new DownloadItemPaneEntity(-1, downloadItemPane);
            e1.setAbstractDataBean(abstractDataBean);

            downloadItemPane.setEntity(e1);
            downloadItemPane.setListView(listView);



            Platform.runLater(() -> {
                listView.getItems().add(e1);
            });

            downloadItemPane.getLocal().setState(DownloaderController.EXECUTE);
            abstractDataBean.setPane(downloadItemPane);
            abstractDataBean.setName(UUID.randomUUID().toString());
            abstractDataBean.setDirFile(new File("echo"));
            abstractDataBean.setDownloadPane(downloadingPaneContainer.getDownloadPane());
            abstractDataBean.setRefererUrl(href);
            abstractDataBean.setSize(new AtomicLong());
            abstractDataBean.setAllSize(new AtomicLong());
            abstractDataBean.setQuality(-1);

            new EchoDownloader().download(abstractDataBean);

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
