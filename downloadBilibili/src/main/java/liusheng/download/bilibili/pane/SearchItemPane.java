package liusheng.download.bilibili.pane;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import liusheng.download.bilibili.BilibiliDownloadAction;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadInterface.SearchItem;


public class SearchItemPane extends HBox {

    public Label getIndexLabel() {
        return indexLabel;
    }

    private Label indexLabel;
    private final Label nameLabel;
    private DownloadingPaneContainer downloadingPaneContainer;
    private final ImageView imageView;
    private final Label authorLabel;
    private final HBox operationLabel;
    private final JFXButton download;

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
        return operationLabel;
    }

    public JFXButton getDownload() {
        return download;
    }

    public SearchItemPane(int index, SearchItem item, DownloadingPaneContainer downloadingPaneContainer) {
        super();
        indexLabel = new Label(String.valueOf(index));
        nameLabel = new Label(item.getTitle());
        this.downloadingPaneContainer = downloadingPaneContainer;


        imageView = new ImageView();

        BindUtils.bind(imageView.fitHeightProperty(), heightProperty());
        BindUtils.bind(imageView.fitWidthProperty(), widthProperty().multiply(0.15));

        authorLabel = new Label(item.getAuthor());
        operationLabel = new HBox();

        download = new JFXButton("下载");

        download.setAlignment(Pos.CENTER);
        download.setOnAction(new BilibiliDownloadAction(item.getHref(),downloadingPaneContainer));

        setSize(download, 1);
        operationLabel.getChildren().addAll(download);
        setAligment(indexLabel);
        setAligment(nameLabel);
        setAligment(authorLabel);

        operationLabel.setAlignment(Pos.CENTER_LEFT);
        setSize(indexLabel, 0.1);
        setSize(nameLabel, 0.20);
        setSize(authorLabel, 0.15);
        setSize(operationLabel, 0.4);

        getChildren().addAll(indexLabel, nameLabel, imageView, authorLabel, operationLabel);
    }

    private void setAligment(Label indexLabel) {
        indexLabel.setAlignment(Pos.CENTER);
    }

    private void setSize(Region indexLabel, double b) {
        BindUtils.bind(indexLabel.prefWidthProperty(), this.widthProperty().multiply(b));
        BindUtils.bind(indexLabel.prefHeightProperty(), this.heightProperty());
    }
}
