package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;

public class QualityJFXComboBox extends JFXComboBox<Label> {
    public QualityJFXComboBox() {
        getItems().add(new Label("fluent"));
        getItems().add(new Label("low"));
        getItems().add(new Label("mid"));
        getItems().add(new Label("high"));
        getItems().add(new Label("super high"));
    }
}
