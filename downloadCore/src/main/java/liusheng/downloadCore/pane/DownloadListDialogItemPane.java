package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DownloadListDialogItemPane extends HBox {
    private final Label indexLabel = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final Label textLabel = new Label();

    public Label getIndexLabel() {
        return indexLabel;
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public Label getTextLabel() {
        return textLabel;
    }

    public DownloadListDialogItemPane(String index, String text) {
        super();

        indexLabel.setText(index);
        indexLabel.setPrefSize(30,30);
        checkBox.setPrefSize(30,30);
        textLabel.setPrefSize(200,30);
        textLabel.setText(text);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(260,40);
        getChildren().addAll(indexLabel, checkBox, textLabel);
    }
}
