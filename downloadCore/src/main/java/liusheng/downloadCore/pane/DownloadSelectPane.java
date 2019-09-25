package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;
import org.apache.log4j.Logger;

public class DownloadSelectPane extends VBox {
    private HBox top = new HBox();
    private VBox middle = new VBox();
    private final Logger logger = Logger.getLogger(DownloadListDialog.class);
    JFXComboBox<Label> quality;
    JFXListView<DownloadListDialogItemPane> listView;
    private JFXButton downloadButton;

    public JFXButton getDownloadButton() {
        return downloadButton;
    }

    public JFXComboBox<Label> getQuality() {
        return quality;
    }

    public JFXListView<DownloadListDialogItemPane> getListView() {
        return listView;
    }

    public DownloadSelectPane() {
        super();

        JFXCheckBox allSelect = new JFXCheckBox("全选");

        downloadButton = new JFXButton("下载");

        quality = new QualityJFXComboBox();
        quality.getSelectionModel().selectLast();


        top.getChildren().addAll(allSelect, quality, downloadButton);


        listView = new JFXListView();

        middle.setStyle("");
        middle.getChildren().addAll(listView);


        allSelect.setOnMouseClicked(event -> {
            listView.getItems().forEach(d -> {
                d.getCheckBox().setSelected(allSelect.isSelected());
            });
        });

        BindUtils.bind(allSelect.prefWidthProperty(), top.widthProperty().multiply(0.3));
        BindUtils.bind(downloadButton.prefWidthProperty(), top.widthProperty().multiply(0.3));
        BindUtils.bind(quality.prefWidthProperty(), top.widthProperty().multiply(0.3));
        BindUtils.bind(downloadButton.prefHeightProperty(), top.heightProperty());
        BindUtils.bind(allSelect.prefHeightProperty(), top.heightProperty());
        BindUtils.bind(quality.prefHeightProperty(), top.heightProperty());

        BindUtils.bind(listView.prefHeightProperty(), middle.heightProperty());
        BindUtils.bind(listView.prefWidthProperty(), middle.widthProperty());

        BindUtils.bind(top.prefWidthProperty(), widthProperty());
        BindUtils.bind(middle.prefWidthProperty(), widthProperty());
        BindUtils.bind(top.prefHeightProperty(), heightProperty().multiply(0.15));
        BindUtils.bind(middle.prefHeightProperty(), heightProperty().multiply(0.85));

        top.setAlignment(Pos.CENTER);
        middle.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(top, middle);
    }
}
