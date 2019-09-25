package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadInterface.DownloaderController;

public class DownloadItemPane extends VBox {


    private Label pathLabel;
    private Label stateLabel;
    private DownloadItemPaneEntity entity;

    public DownloadItemPaneEntity getEntity() {
        return entity;
    }

    public void setEntity(DownloadItemPaneEntity entity) {
        this.entity = entity;
    }

    private JFXProgressBar downloadProgressBar;
    private HBox top;
    private HBox bottom;
    private HBox middle;
    private JFXButton cancel;
    private JFXButton pause;
    private JFXButton retry;

    public JFXButton getRetry() {
        return retry;
    }

    private Label progressValue;
    private DownloaderController global;
    private DownloaderController local;

    public Label getPathLabel() {
        return pathLabel;
    }

    public Label getStateLabel() {
        return stateLabel;
    }

    public JFXProgressBar getDownloadProgressBar() {
        return downloadProgressBar;
    }

    public HBox getTop() {
        return top;
    }

    public HBox getBottom() {
        return bottom;
    }

    public JFXButton getButton1() {
        return cancel;
    }

    public JFXButton getPause() {
        return pause;
    }

    public Label getProgressValue() {
        return progressValue;
    }

    public HBox getMiddle() {
        return middle;
    }

    public DownloaderController getLocal() {
        return local;
    }

    private JFXListView<DownloadItemPaneEntity> listView;

    public JFXListView<DownloadItemPaneEntity> getListView() {
        return listView;
    }

    public void setListView(JFXListView<DownloadItemPaneEntity> listView) {
        this.listView = listView;
    }


    public DownloadItemPane(DownloaderController local) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.local = local;

        downloadProgressBar = new JFXProgressBar();
        top = new HBox();
        middle = new HBox();
        bottom = new HBox();
        stateLabel = new Label("正在下载...");
        pathLabel = new Label("C:\\Users\\LiuSheng\\IdeaProjects\\testGoogle\\src\\main\\java");
        cancel = new JFXButton("取消");
        retry = new JFXButton("重试");
        progressValue = new Label("");
        pause = new JFXButton("暂停");


        middle.getChildren().addAll(pathLabel, stateLabel);
        bottom.getChildren().addAll(cancel, retry
                , pause);

        bottom.prefWidthProperty().bind(this.
                widthProperty());

        top.getChildren().addAll(downloadProgressBar, progressValue);


        progressValue.setAlignment(Pos.CENTER);
        downloadProgressBar.setProgress(0);


        cancel.setOnAction(e -> {
            local.cancel();
        });

        pause.setOnAction(e -> {
            int state = local.getState();
            if (state == DownloaderController.EXECUTE) {
                pause.setText("继续");
            } else if (state == DownloaderController.PAUSE) {
                pause.setText("暂停");
            }
            local.pause();
        });

        top.setAlignment(Pos.CENTER_LEFT);
        BindUtils.bind(top.prefWidthProperty(), this.widthProperty());
        BindUtils.bind(top.prefHeightProperty(), this.heightProperty().multiply(0.3));
        bottom.setAlignment(Pos.CENTER);
        BindUtils.bind(bottom.prefWidthProperty(), this.widthProperty());
        BindUtils.bind(bottom.prefHeightProperty(), this.heightProperty().multiply(0.3));
        middle.prefWidthProperty().bind(this.widthProperty());
        middle.setAlignment(Pos.CENTER_LEFT);
        BindUtils.bind(middle.prefWidthProperty(), this.widthProperty());
        BindUtils.bind(middle.prefHeightProperty(), this.heightProperty().multiply(0.4));
        BindUtils.bind(pathLabel.prefHeightProperty(), middle.heightProperty());
        BindUtils.bind(pathLabel.prefWidthProperty(), middle.widthProperty().multiply(0.5));
        BindUtils.bind(stateLabel.prefWidthProperty(), middle.widthProperty().multiply(0.5));
        BindUtils.bind(stateLabel.prefHeightProperty(), middle.heightProperty());
        BindUtils.bind(progressValue.prefHeightProperty(), top.heightProperty());
        BindUtils.bind(progressValue.prefWidthProperty(), top.prefWidthProperty().multiply(0.2));
        BindUtils.bind(downloadProgressBar.prefHeightProperty(), top.heightProperty());
        BindUtils.bind(downloadProgressBar.prefWidthProperty(), top.widthProperty().multiply(0.8));
        BindUtils.bind(cancel.prefWidthProperty(), bottom.widthProperty().multiply(0.3));
        BindUtils.bind(retry.prefWidthProperty(), bottom.widthProperty().multiply(0.3));
        BindUtils.bind(pause.prefWidthProperty(), bottom.widthProperty().multiply(0.3));
        BindUtils.bind(pause.prefHeightProperty(), bottom.heightProperty());
        BindUtils.bind(retry.prefHeightProperty(), bottom.heightProperty());
        BindUtils.bind(cancel.prefHeightProperty(), bottom.heightProperty());

        HBox.setMargin(cancel, new Insets(0, 10, 0, 5));
        HBox.setMargin(pause, new Insets(0, 5, 0, 10));
        HBox.setMargin(retry, new Insets(0, 5, 0, 5));

        getChildren().addAll(top, middle, bottom);
        setBorder(new Border(new BorderStroke(Paint.valueOf("blue"), BorderStrokeStyle.SOLID
                , new CornerRadii(0), new BorderWidths(2))));

    }

}
