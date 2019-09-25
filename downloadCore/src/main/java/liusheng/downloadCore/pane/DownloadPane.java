package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;

import java.util.HashMap;
import java.util.Map;

public class DownloadPane extends VBox {

    private final DownloadedPane downloadedPane = new DownloadedPane(this);
    private final DownloadingPane downloadingPane = new DownloadingPane(this);
    private final DownloadControllerPane downloadControllerPane = new DownloadControllerPane();
    private final Map<JFXButton, Node> nodeMap = new HashMap<>();

    public DownloadedPane getDownloadedPane() {
        return downloadedPane;
    }

    public DownloadingPane getDownloadingPane() {
        return downloadingPane;
    }

    public DownloadControllerPane getDownloadControllerPane() {
        return downloadControllerPane;
    }

    public DownloadPane() {
        super();
        JFXButton downloadedButton = downloadControllerPane.getDownloadedButton();
        JFXButton downloadingButton = downloadControllerPane.getDownloadingButton();

        ObservableList<Node> children = this.getChildren();
        downloadingButton.setOnAction(e->{
            int size = children.size();
            if (size > 1) {
                children.remove(1,size);
            }

            children.add(downloadingPane);
        });

        downloadedButton.setOnAction(e->{
            int size = children.size();
            if (size > 1) {
                children.remove(1,size);
            }

            children.add(downloadedPane);
        });
        nodeMap.put(downloadedButton, downloadedPane);
        nodeMap.put(downloadingButton, downloadingPane);

        BindUtils.bind(downloadControllerPane.prefWidthProperty(),this.widthProperty());
        BindUtils.bind(downloadedPane.prefWidthProperty(),this.widthProperty());
        BindUtils.bind(downloadingPane.prefWidthProperty(),this.widthProperty());


        BindUtils.bind(downloadControllerPane.prefHeightProperty(),this.heightProperty().multiply(0.1));
        BindUtils.bind(downloadedPane.prefHeightProperty(),this.heightProperty().multiply(0.9));
        BindUtils.bind(downloadingPane.prefHeightProperty(),this.heightProperty().multiply(0.9));

        children.addAll(downloadControllerPane,downloadingPane);
    }
}
