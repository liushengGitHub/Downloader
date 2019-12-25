package liusheng.download.kugou;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DefaultDownloaderController;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class KugouDownloadAction implements EventHandler<ActionEvent> {
    private final String href;
    private final DownloadingPaneContainer downloadingPaneContainer;
    private final Gson gson = new Gson();

    public KugouDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {

        this.href = href;
        this.downloadingPaneContainer = downloadingPaneContainer;
    }

    @Override
    public void handle(ActionEvent event) {
        FailListExecutorService.commonExecutorServicehelp().execute(new FailTask(() -> {

            try {
                JFXListView<DownloadItemPaneEntity> listView = downloadingPaneContainer.getListView();
                String body = ConnectionUtils.getConnection(href).execute().body();

                SongEntity songEntity = gson.fromJson(body, SongEntity.class);
                DownloadItemPane downloadItemPane = new DownloadItemPane(new DefaultDownloaderController());
                DownloadItemPaneEntity e1 = new DownloadItemPaneEntity(-1, downloadItemPane);
                e1.setAbstractVideoBean(songEntity);

                downloadItemPane.setEntity(e1);
                downloadItemPane.setEntity(e1);
                downloadItemPane.setListView(listView);

                if (StringUtils.isEmpty(songEntity.getData().getPlay_url())) {


                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.INFORMATION, "无法获取下载的url,请换一首").showAndWait();
                    });

                    return;
                }

                Platform.runLater(() -> {
                    listView.getItems().add(e1);
                });

                songEntity.setPane(downloadItemPane);
                songEntity.setName(StringUtils.fileNameHandle(songEntity.getData().getAlbum_name()));
                songEntity.setDirFile(new File("mp3"));
                songEntity.setDownloadPane(downloadingPaneContainer.getDownloadPane());
                songEntity.setRefererUrl(href);
                songEntity.setSize(new AtomicLong());
                songEntity.setAllSize(new AtomicLong());
                songEntity.setQuality(-1);


                new KugouMp3Downloader().download(songEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
