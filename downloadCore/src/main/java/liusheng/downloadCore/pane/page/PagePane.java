package liusheng.downloadCore.pane.page;

import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;

public class PagePane  extends VBox {
    private VBox containerPane;
    private PageControllerHBox controller;

    public VBox getContainerPane() {
        return containerPane;
    }

    public PageControllerHBox getController() {
        return controller;
    }

    public PagePane() {
        super();
        controller = new PageControllerHBox();
        controller.init();
        containerPane = new VBox();

        BindUtils.bind(controller.prefHeightProperty(), this.heightProperty().multiply(0.15));
        BindUtils.bind(containerPane.prefHeightProperty(), this.heightProperty().multiply(0.85));

        getChildren().addAll(controller, containerPane);
    }
}
