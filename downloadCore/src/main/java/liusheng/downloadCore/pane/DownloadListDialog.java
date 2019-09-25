package liusheng.downloadCore.pane;

import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.DownloadProcessor;
import org.apache.log4j.Logger;

public class DownloadListDialog {

    private VBox main = new VBox();
    private HBox top = new HBox();
    private HBox middle = new HBox();
    private HBox bottom = new HBox();

    private DownloadingPaneContainer downloadingPaneContainer;

    private final Logger logger = Logger.getLogger(DownloadListDialog.class);
    private final DownloadProcessor downloadProcessor;

    public DownloadListDialog(
            DownloadingPaneContainer downloadingPaneContainer, DownloadProcessor downloadProcessor) {
        this.downloadingPaneContainer = downloadingPaneContainer;
        this.downloadProcessor = downloadProcessor;
    }

    public void showAndWait() {

        JFXAlert<Object> alert = new JFXAlert<>();


        JFXCheckBox allSelect = new JFXCheckBox("全选");


        JFXComboBox<Label> quality = new QualityJFXComboBox();


        quality.getSelectionModel().selectLast();
        allSelect.setPrefSize(120, 30);
        quality.setPrefSize(180, 30);
        HBox.setMargin(allSelect, new Insets(0, 20, 0, 30));
        HBox.setMargin(quality, new Insets(0, 30, 0, 20));
        top.getChildren().addAll(allSelect, quality);

        JFXButton cancel = new JFXButton("取消");
        cancel.setPrefWidth(120);
        cancel.setPrefHeight(30);
        JFXButton ok = new JFXButton("确定");
        ok.setPrefWidth(120);
        ok.setPrefHeight(30);
        HBox.setMargin(ok, new Insets(0, 50, 0, 30));
        HBox.setMargin(cancel, new Insets(0, 30, 0, 50));
        bottom.getChildren().addAll(ok, cancel);

        JFXListView<DownloadListDialogItemPane> listView = new JFXListView();


        middle.getChildren().addAll(listView);

        listView.setPrefSize(300, 300);

        allSelect.setOnMouseClicked(event -> {
            listView.getItems().forEach(d -> {
                d.getCheckBox().setSelected(allSelect.isSelected());
            });
        });
        cancel.setOnAction(e -> {
            alert.close();
        });
        downloadProcessor.before(listView);
        ok.setOnAction(e -> {
            downloadProcessor.processor(quality, listView, downloadingPaneContainer);
            alert.close();
        });
        top.setPrefHeight(50);
        middle.setPrefHeight(300);
        bottom.setPrefHeight(50);
        top.setPrefWidth(300);
        middle.setPrefWidth(300);
        bottom.setPrefWidth(300);

        top.setAlignment(Pos.CENTER_LEFT);
        middle.setAlignment(Pos.CENTER_LEFT);
        bottom.setAlignment(Pos.CENTER_LEFT);
        main.getChildren().addAll(top, middle, bottom);
        alert.setSize(300, 400);
        alert.setContent(main);
        ok.requestFocus();
        alert.showAndWait();

    }
}
