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
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPageParser;

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
        MediaView mediaView = playerPane.getMediaView();
        ListExecutorService.commonExecutorService()
                .execute(() -> {
                    String body = null;
                    try {
                        body = ConnectionUtils.getConnection(item.getHref()).execute().body();
                        SongEntity songEntity = gson.fromJson(body, SongEntity.class);
                        String playUrl = songEntity.getData().getPlay_url();

                        if (StringUtils.isEmpty(playUrl)) {
                            throw new RuntimeException();
                        }

                        Media media = new Media(playUrl);
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        Platform.runLater(() -> {
                            player.setTitle(songEntity.getName());
                            if (!player.isShowing()) {
                                return;
                            }
                            int height = media.getHeight();
                            int width = media.getWidth();
                            if (width != 0 && height != 0) {
                                if (width * 1.0 / height > 600.0 / 400) {
                                    mediaView.setFitWidth(600);
                                    mediaView.setFitHeight(400 * (600.0 / width));
                                } else {
                                    mediaView.setFitHeight(400);
                                    mediaView.setFitWidth(600 * (400.0 / height));
                                }
                            } else {
                                mediaView.setFitWidth(0);
                                mediaView.setFitHeight(0);
                                playerPane.getMain().getChildren().add(new Label("播放的无画面"));
                            }
                            JFXButton playButton = ProcessPlayButton(playerPane, mediaPlayer);

                            processPlaySolider(playerPane, mediaPlayer);

                            processVolumnSolidaer(playerPane, mediaPlayer);
                            mediaView.setMediaPlayer(mediaPlayer);

                            playButton.fire();
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.INFORMATION, "播放失败").showAndWait();
                        });
                    }
                });
        player.setOnCloseRequest((e1) -> {
            if (Objects.nonNull(mediaView.getMediaPlayer())) {
                mediaView.getMediaPlayer().stop();
            }
        });
        player.setContent(playerPane);
        player.show();
    }

    private void processVolumnSolidaer(MusicPlayerPane playerPane, MediaPlayer mediaPlayer) {
        mediaPlayer.volumeProperty().bind(playerPane.getVolumnSolider().valueProperty());
    }

    private Duration seekTime = null;

    private void processPlaySolider(MusicPlayerPane playerPane, MediaPlayer mediaPlayer) {
        JFXSlider playSolider = playerPane.getPlaySolider();
        mediaPlayer.currentTimeProperty().addListener((a, o, n) -> {
            //?
            if (seekTime != null && !seekTime.equals(n)) {
                return;
            }else {
                seekTime = null;
            }
            playSolider.setValue(n.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
        });
        playSolider.valueProperty().addListener((a, o, n) -> {
            if (playSolider.isValueChanging()) {
                seekTime = Duration.millis(mediaPlayer.getTotalDuration().toMillis() * n.doubleValue() / 100);
                mediaPlayer.seek(seekTime);
            }
        });
        playSolider.setOnMouseClicked((e)->{
            seekTime = Duration.millis(mediaPlayer.getTotalDuration().toMillis() * playSolider.getValue() / 100);
            mediaPlayer.seek(seekTime);
        });
    }

    private JFXButton ProcessPlayButton(MusicPlayerPane playerPane, MediaPlayer mediaPlayer) {
        JFXButton playButton = playerPane.getPlayButton();

        playButton.setOnAction((e1) -> {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.UNKNOWN) {
                mediaPlayer.play();
                playButton.setText("暂停");
            } else if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playButton.setText("播放");
            }
        });
        return playButton;
    }
}
