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


public class SearchDownloadItemPane extends AbstractSearchItemPane {


    private final JFXButton download;


    public HBox getOperationLabel() {
        return Objects.nonNull(operationLabel) ? operationLabel : getOperationLabel0();
    }

    protected HBox getOperationLabel0() {
        return new HBox();
    }

    public JFXButton getDownload() {
        return download;
    }

    public SearchDownloadItemPane(int index, SearchItem item, DownloadingPaneContainer downloadingPaneContainer) {
        super(index, item, downloadingPaneContainer);

        download = new JFXButton("下载");

        download.setAlignment(Pos.CENTER);

        setSize(download, operationLabel,1);

        operationLabel.getChildren().addAll(download);
    }

}
