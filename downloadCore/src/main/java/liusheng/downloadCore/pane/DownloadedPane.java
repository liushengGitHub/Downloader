package liusheng.downloadCore.pane;

import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;

public class DownloadedPane extends VBox {

    private DownloadedPaneController downloadPaneController;
    private DownloadedPaneContainer downloadedPaneContainer;
    private DownloadPane downloadPane;


    public DownloadedPaneController getDownloadPaneController() {
        return downloadPaneController;
    }

    public DownloadedPaneContainer getDownloadedPaneContainer() {
        return downloadedPaneContainer;
    }

    public DownloadedPane(DownloadPane downloadPane) {
        super();
        this.downloadPane = downloadPane;

        downloadedPaneContainer = new DownloadedPaneContainer(downloadPane);
        downloadPaneController = new DownloadedPaneController();

        downloadPaneController.getAllDelete().setOnAction(e->{
            downloadedPaneContainer.getListView().getItems().clear();
        });

        BindUtils.bind(downloadPaneController.prefWidthProperty(), this.widthProperty());
        BindUtils.bind(downloadPaneController.prefHeightProperty(), this.heightProperty().multiply(0.1));
        BindUtils.bind(downloadedPaneContainer.prefWidthProperty(), this.widthProperty());
        BindUtils.bind(downloadedPaneContainer.prefHeightProperty(), this.heightProperty().multiply(0.9));


        this.getChildren().addAll(downloadPaneController, downloadedPaneContainer);
    }
}
