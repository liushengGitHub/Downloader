package liusheng.download.kugou;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.AbstractSearchPaneParser;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.pane.*;
import liusheng.downloadCore.player.CustomeMediaPlayer;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPageParser;

import java.io.IOException;
import java.util.Objects;

public class KugouDownloadSearchParser extends AbstractSearchPaneParser {

    private final Gson gson = new Gson();

    protected AbstractDownloadAction getDownloadHandler0(String href, DownloadingPaneContainer downloadingPaneContainer) {
        return new KugouDownloadAction(href, downloadingPaneContainer);
    }

    @Override
    protected Parser getInfoParser0() {
        return new KugouSearchInfoParser();
    }

    @Override
    protected SearchPageParser getPageParser0() {
        return new KugouSearchPageParser();
    }

    @Override
    protected AbstractSearchItemPane getSearchItemPane(DownloadingPane pane, Integer i, SearchItem item) {
        SearchMusicItemPane pane1 = new SearchMusicItemPane(i, item, pane.getDownloadingPaneContainer());
        pane1.getDownload().setOnAction(getDownloadHandler0(item.getHref(), pane.getDownloadingPaneContainer()));
        pane1.getPlay().setOnAction(e -> {
            initPlayPane(item);
        });
        return pane1;
    }

    private void initPlayPane(SearchItem item) {
        JFXAlert<Object> player = new JFXAlert<>();
        player.setSize(600, 480);
        player.setResizable(false);
        MusicPlayerPane playerPane = new MusicPlayerPane();
        ListExecutorService.commonExecutorService()
                .execute(()->{

                    try {
                        String body  = ConnectionUtils.getConnection(item.getHref()).execute().body();
                        SongEntity songEntity = gson.fromJson(body, SongEntity.class);
                        String playUrl = songEntity.getData().getPlay_url();
                        player.setTitle(songEntity.getName());
                        CustomeMediaPlayer customeMediaPlayer = new CustomeMediaPlayer(playUrl,playerPane,player);
                        customeMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        player.close();
                    }

                });
        player.setContent(playerPane);
        player.show();
    }


}
