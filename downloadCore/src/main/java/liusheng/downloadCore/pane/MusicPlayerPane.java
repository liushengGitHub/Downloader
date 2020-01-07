package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * 年: 2020  月: 01 日: 07 小时: 12 分钟: 20
 * 用户名: LiuSheng
 */

public class MusicPlayerPane extends VBox {
    private final VBox main;
    private final HBox controller;
    private final MediaView mediaView;

    private final JFXButton playButton;
    private final JFXSlider playSolider;
    private final JFXSlider volumnSolider;

    public JFXButton getPlayButton() {
        return playButton;
    }

    public JFXSlider getPlaySolider() {
        return playSolider;
    }

    public JFXSlider getVolumnSolider() {
        return volumnSolider;
    }

    public VBox getMain() {
        return main;
    }

    public HBox getController() {
        return controller;
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public MusicPlayerPane() {
        super();
        main = new VBox();
        main.setAlignment(Pos.CENTER);
        mediaView = new MediaView();
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(600);
        main.getChildren().add(mediaView);
        main.setPrefSize(600, 400);
        controller = new HBox();

        playButton = new JFXButton("播放");

        playButton.setAlignment(Pos.CENTER);
        playButton.setPrefWidth(100);
        playSolider = new JFXSlider(0, 100, 0);
        playSolider.setPrefWidth(450);
        volumnSolider = new JFXSlider(0, 100, 100);
        volumnSolider.setPrefWidth(130);

        controller.setAlignment(Pos.CENTER);
        controller.getChildren().addAll(playButton, playSolider, volumnSolider);

        controller.setPrefSize(600, 80);
        this.getChildren().addAll(main, controller);
    }
}
