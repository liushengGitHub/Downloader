package liusheng.downloadCore.player;

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
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.pane.MusicPlayerPane;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.StringUtils;

import java.util.Objects;

/**
 * 年: 2020  月: 01 日: 07 小时: 18 分钟: 24
 * 用户名: LiuSheng
 */

public class CustomeMediaPlayer {

    private final String url;
    private final MusicPlayerPane playerPane;
    private final JFXAlert<Object> player;


    public CustomeMediaPlayer(String url, MusicPlayerPane playerPane, JFXAlert<Object> player) {
        this.url = url;
        this.playerPane = playerPane;
        this.player = player;
    }

    public void start() {
        MediaView mediaView = playerPane.getMediaView();
        try {
            if (StringUtils.isEmpty(url)) {
                throw new RuntimeException();
            }

            Media media = new Media(url);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(false);
            Platform.runLater(() -> {
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
                mediaView.setMediaPlayer(mediaPlayer);
                processVolumnSolidaer(playerPane, mediaPlayer);
                mediaPlayer.statusProperty().addListener((a,o,n)->{
                    if (n == MediaPlayer.Status.READY){

                         playButton.fire();
                    }
                });


            });
        } catch (Exception ex) {
            ex.printStackTrace();
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.INFORMATION, "播放失败").showAndWait();
            });
        }
        player.setOnCloseRequest((e1) -> {
            if (Objects.nonNull(mediaView.getMediaPlayer())) {
                mediaView.getMediaPlayer().stop();
            }
        });
    }

    public void stop() {

    }

    private void processVolumnSolidaer(MusicPlayerPane playerPane, MediaPlayer mediaPlayer) {
        mediaPlayer.volumeProperty().bind(playerPane.getVolumnSolider().valueProperty().divide(100.0));
    }

    private Duration seekTime = null;

    private void processPlaySolider(MusicPlayerPane playerPane, MediaPlayer mediaPlayer) {
        JFXSlider playSolider = playerPane.getPlaySolider();
        mediaPlayer.currentTimeProperty().addListener((a, o, n) -> {
            //?
            if (seekTime != null && (n.toMillis() - seekTime.toMillis()) < 200) {
                System.out.println(seekTime.toMillis() + " = " + n.toMillis());
                seekTime = null;
            } else if (seekTime != null) {
                return;
            }
            playSolider.setValue(n.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
        });
        playSolider.valueProperty().addListener((a, o, n) -> {
            if (playSolider.isValueChanging()) {
                seekTime = Duration.millis(mediaPlayer.getTotalDuration().toMillis() * n.doubleValue() / 100);
                mediaPlayer.seek(seekTime);
            }
        });

     /*   playSolider.setOnMouseClicked((e) -> {
            seekTime = Duration.millis(mediaPlayer.getTotalDuration().toMillis() * playSolider.getValue() / 100);
            System.out.println(seekTime.toMillis());
            mediaPlayer.seek(seekTime);
        });*/
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
