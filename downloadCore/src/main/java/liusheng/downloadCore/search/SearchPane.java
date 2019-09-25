package liusheng.downloadCore.search;

import javafx.scene.layout.VBox;
import liusheng.downloadCore.util.BindUtils;

public class SearchPane extends VBox {
    /**
     * 可以自定义的
     */
    private VBox containerPane;
    private SearchControllerHBox controller;

    public VBox getContainerPane() {
        return containerPane;
    }

    public SearchControllerHBox getController() {
        return controller;
    }

    public SearchPane() {
        super();
        controller = new SearchControllerHBox();
        controller.init();
        containerPane = new VBox();


        BindUtils.bind(controller.prefHeightProperty(), this.heightProperty().multiply(0.15));
        BindUtils.bind(containerPane.prefHeightProperty(), this.heightProperty().multiply(0.85));

        getChildren().addAll(controller, containerPane);
    }
}
