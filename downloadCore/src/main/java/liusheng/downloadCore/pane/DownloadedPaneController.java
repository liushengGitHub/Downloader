package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadCore.util.BindUtils;

public class DownloadedPaneController extends HBox {

    private JFXButton allDelete;
    private JFXButton allPause;

    public JFXButton getAllDelete() {
        return allDelete;
    }

    public JFXButton getAllPause() {
        return allPause;
    }

    public DownloadedPaneController() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        allDelete = new JFXButton("删除全部");
        allPause = new JFXButton("卢本伟流弊");

        BindUtils.bind(allPause.prefHeightProperty(),this.heightProperty().multiply(0.8));
        BindUtils.bind(allPause.prefWidthProperty(),this.widthProperty().multiply(0.15));
        BindUtils.bind(allDelete.prefHeightProperty(),this.heightProperty().multiply(0.8));
        BindUtils.bind(allDelete.prefWidthProperty(),this.widthProperty().multiply(0.15));

        HBox.setMargin(allDelete,new Insets(0,30,0,30));
        HBox.setMargin(allPause,new Insets(0,30,0,30));
        this.getChildren().addAll(allPause, allDelete);
    }
}
