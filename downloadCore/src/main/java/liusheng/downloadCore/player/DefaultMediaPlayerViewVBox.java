package liusheng.downloadCore.player;

import cn.hutool.core.util.ClassLoaderUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.util.BindUtils;

public class DefaultMediaPlayerViewVBox extends VBox {

    private final MediaView mediaView;
    private  MediaPlayer mediaPlayer;
    private boolean flag = false;

    public MediaView getMediaView() {
        return mediaView;
    }

    public DefaultMediaPlayerViewVBox(String url) {
        super();

        mediaView = new MediaView();
        HBox controller = new HBox();

        Image playImage = new Image(
                ClassLoaderUtil.getClassLoader().getResource("icon/play.png").toString()
        );

        Image pauseImage = new Image(
                ClassLoaderUtil.getClassLoader().getResource("icon/pause.png").toString()
        );
        ImageView imageView = new ImageView();
        imageView.setImage(playImage);

        JFXSlider progress = new JFXSlider();
        JFXButton player = new JFXButton("", imageView);
        JFXSlider volumn = new JFXSlider();


        BindUtils.bind(progress.prefHeightProperty(), controller.heightProperty());
        BindUtils.bind(progress.prefWidthProperty(), controller.widthProperty().subtract(150));

        BindUtils.bind(player.prefHeightProperty(), controller.heightProperty());
        BindUtils.bind(volumn.prefHeightProperty(), controller.heightProperty());

        player.setPrefWidth(50);
        volumn.setPrefWidth(100);


        volumn.setValue(50);
        progress.setValue(0);

        BindUtils.bind(controller.prefWidthProperty(), widthProperty());

        controller.setPrefHeight(50);

        //BindUtils.bind(mediaView.fitHeightProperty(),mainPane.heightProperty());
        BindUtils.bind(mediaView.fitHeightProperty(), heightProperty().subtract(50));


        mediaPlayer = new MediaPlayer(new Media(url));
        Platform.runLater(() -> {
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.getMediaPlayer().volumeProperty().bind(volumn.valueProperty());

            player.setOnAction(e -> {

                MediaPlayer.Status status = mediaPlayer.getStatus();

                if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY) {
                    mediaPlayer.play();
                    player.setGraphic(new ImageView(pauseImage));
                } else if (status == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    player.setGraphic(new ImageView(playImage));
                }

            });
            mediaPlayer.statusProperty().addListener((a, o, n) -> {
                if (n == MediaPlayer.Status.READY) {
                    player.fire();
                }
            });
            mediaPlayer.currentTimeProperty().addListener((a, b, c) -> {

                if (!flag) {
                    progress.setValue(progress.getMax() * c.toMillis() / mediaPlayer.getMedia().getDuration().toMillis());
                } else {
                    if (mediaPlayer.getCurrentTime().greaterThan(c)) {
                        flag = false;
                    }
                }
            });


            progress.setOnMouseClicked(e -> {
                flag = true;
                double v = progress.getValue() / progress.getMax();
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(v));
            });


        });

        controller.getChildren().addAll(player, progress, volumn);
        controller.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        setStyle("-fx-background-color: black");
        getChildren().addAll(mediaView, controller);

    }
    public  void close() {
        mediaPlayer.dispose();
    }
}
