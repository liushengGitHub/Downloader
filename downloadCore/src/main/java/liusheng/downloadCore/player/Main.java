package liusheng.downloadCore.player;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import liusheng.downloadCore.util.BindUtils;

import java.net.MalformedURLException;
import java.nio.file.Paths;

public class Main extends Application {
    private boolean flag = false;

    @Override
    public void start(Stage primaryStage) throws Exception {



        Scene scene = new Scene(new DefaultMediaPlayerViewVBox(Paths.get("C:\\Users\\LiuSheng\\Desktop\\一首好聽的日語歌《卒業》藤田麻衣子【中日歌詞Lyrics】.mp4").toUri().toURL().toString()), 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private MediaPlayer getMediaPlayer() throws MalformedURLException {
        return new MediaPlayer(new Media(
                Paths.get("C:\\Users\\LiuSheng\\Desktop\\一首好聽的日語歌《卒業》藤田麻衣子【中日歌詞Lyrics】.mp4").toUri().toURL().toString()
        ));

    }
}
