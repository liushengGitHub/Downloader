package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadCore.util.BindUtils;

public class DownloadingPaneController extends HBox {

    private JFXButton allCancel;
    private JFXButton allPause;

    public JFXButton getAllCancel() {
        return allCancel;
    }

    public JFXButton getAllPause() {
        return allPause;
    }

    public DownloadingPaneController() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        allCancel = new JFXButton("取消所有");
        allPause = new JFXButton("暂停所有");

        BindUtils.bind(allPause.prefHeightProperty(),this.heightProperty().multiply(0.8));
        BindUtils.bind(allPause.prefWidthProperty(),this.widthProperty().multiply(0.15));
        BindUtils.bind(allCancel.prefHeightProperty(),this.heightProperty().multiply(0.8));
        BindUtils.bind(allCancel.prefWidthProperty(),this.widthProperty().multiply(0.15));

        HBox.setMargin(allCancel,new Insets(0,30,0,30));
        HBox.setMargin(allPause,new Insets(0,30,0,30));
        this.getChildren().addAll(allPause,allCancel);
    }
}
