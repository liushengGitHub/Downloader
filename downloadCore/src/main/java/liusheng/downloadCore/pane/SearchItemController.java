package liusheng.downloadCore.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import liusheng.downloadCore.util.BindUtils;

public class SearchItemController extends HBox {

    public SearchItemController() {
        super();
        Label indexLabel = new Label("索引");
        Label nameLabel = new Label("名字");
        Label imageLabel = new Label("图片");
        Label authorLabel = new Label("作者");
        Label operationLabel = new Label("操作");

        setAligment(indexLabel);
        setAligment(nameLabel);
        setAligment(imageLabel);
        setAligment(authorLabel);
        setAligment(operationLabel);

        setSize(indexLabel, 0.15);
        setSize(nameLabel, 0.15);
        setSize(imageLabel, 0.15);
        setSize(authorLabel, 0.15);
        setSize(operationLabel, 0.4);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(indexLabel, nameLabel, imageLabel, authorLabel, operationLabel);
    }

    private void setAligment(Label indexLabel) {
        indexLabel.setAlignment(Pos.CENTER);
    }

    private void setSize(Region indexLabel, double b) {
        BindUtils.bind(indexLabel.prefWidthProperty(), this.widthProperty().multiply(b));
        BindUtils.bind(indexLabel.prefHeightProperty(), this.heightProperty());
    }
}
