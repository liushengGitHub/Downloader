package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadInterface.SearchItem;

import java.util.Objects;


public class AbstractSearchItemPane extends HBox {

    public Label getIndexLabel() {
        return indexLabel;
    }

    private Label indexLabel;
    private final Label nameLabel;
    private DownloadingPaneContainer downloadingPaneContainer;
    private final ImageView imageView;
    private final Label authorLabel;
    protected final HBox operationLabel;

    public Label getNameLabel() {
        return nameLabel;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Label getAuthorLabel() {
        return authorLabel;
    }

    public HBox getOperationLabel() {
        return Objects.nonNull(operationLabel) ? operationLabel : getOperationLabel0();
    }

    protected HBox getOperationLabel0(){
        return  new HBox();
    }


    public AbstractSearchItemPane(int index, SearchItem item, DownloadingPaneContainer downloadingPaneContainer) {
        super();

        indexLabel = new Label(String.valueOf(index));
        nameLabel = new Label(item.getTitle());
        this.downloadingPaneContainer = downloadingPaneContainer;

        imageView = new ImageView();

        BindUtils.bind(imageView.fitHeightProperty(), heightProperty());
        BindUtils.bind(imageView.fitWidthProperty(), widthProperty().multiply(0.15));

        authorLabel = new Label(item.getAuthor());
        operationLabel = getOperationLabel();
        setAligment(indexLabel);
        setAligment(nameLabel);
        setAligment(authorLabel);

        operationLabel.setAlignment(Pos.CENTER_LEFT);
        setSize(indexLabel, this,0.1);
        setSize(nameLabel, this,0.20);
        setSize(authorLabel, this,0.15);
        setSize(operationLabel, this,0.4);

        getChildren().addAll(indexLabel, nameLabel, imageView, authorLabel, operationLabel);
    }

    protected void setAligment(Label indexLabel) {
        indexLabel.setAlignment(Pos.CENTER);
    }

    protected void setSize(Region indexLabel,Region target, double b) {
        BindUtils.bind(indexLabel.prefWidthProperty(), target.widthProperty().multiply(b));
        BindUtils.bind(indexLabel.prefHeightProperty(), target.heightProperty());
    }
}
