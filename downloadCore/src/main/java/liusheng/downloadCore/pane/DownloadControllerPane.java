package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadCore.util.BindUtils;

public class DownloadControllerPane extends HBox {
    private JFXButton downloadingButton;
    private JFXButton downloadedButton;

    public JFXButton getDownloadingButton() {
        return downloadingButton;
    }

    public JFXButton getDownloadedButton() {
        return downloadedButton;
    }

    public DownloadControllerPane() {
        super();
        downloadingButton = new JFXButton("正在下载");
        downloadedButton = new JFXButton("下载完成");

        setAlignment(Pos.CENTER_LEFT);
        BindUtils.bind(downloadingButton.prefWidthProperty(), this.widthProperty().multiply(0.15));
        BindUtils.bind(downloadingButton.prefHeightProperty(), this.heightProperty());
        BindUtils.bind(downloadedButton.prefWidthProperty(), this.widthProperty().multiply(0.15));
        BindUtils.bind(downloadedButton.prefHeightProperty(), this.heightProperty());

        this.getChildren().addAll(downloadingButton, downloadedButton);
    }
}
