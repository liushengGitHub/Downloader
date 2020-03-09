package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;

/**
 * 年: 2020  月: 01 日: 08 小时: 21 分钟: 59
 * 用户名: LiuSheng
 */

public class DownloadedItemPane extends VBox {

        JFXButton playButton;
    JFXButton deleteFileButton;
    JFXButton openFileButton;
        Label filePathLabel;

    public JFXButton getPlayButton() {
        return playButton;
    }

    public JFXButton getDeleteFileButton() {
        return deleteFileButton;
    }

    public JFXButton getOpenFileButton() {
        return openFileButton;
    }

    public Label getFilePathLabel() {
        return filePathLabel;
    }

    public DownloadedItemPane() {
        super();
        HBox hbox = new HBox();

        filePathLabel = new Label("");
        playButton = new JFXButton("播放");
        openFileButton = new JFXButton("File");
        deleteFileButton = new JFXButton("删除");

        BindUtils.bind(hbox.prefWidthProperty(),widthProperty());
        BindUtils.bind(hbox.prefHeightProperty(),heightProperty());
        BindUtils.bind(filePathLabel.prefHeightProperty(),hbox.heightProperty());
        BindUtils.bind(filePathLabel.prefWidthProperty(),hbox.widthProperty().multiply(0.7));
        BindUtils.bind(playButton.prefHeightProperty(),heightProperty());
        BindUtils.bind(playButton.prefWidthProperty(),hbox.widthProperty().multiply(0.1));
        BindUtils.bind(openFileButton.prefHeightProperty(),heightProperty());
        BindUtils.bind(openFileButton.prefWidthProperty(),hbox.widthProperty().multiply(0.1));
        BindUtils.bind(deleteFileButton.prefHeightProperty(),heightProperty());
        BindUtils.bind(deleteFileButton.prefWidthProperty(),hbox.widthProperty().multiply(0.1));

        hbox.getChildren().addAll(filePathLabel,playButton,openFileButton,deleteFileButton);
        getChildren().addAll(hbox);
    }
}
