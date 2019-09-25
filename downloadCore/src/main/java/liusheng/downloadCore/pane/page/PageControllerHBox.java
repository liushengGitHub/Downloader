package liusheng.downloadCore.pane.page;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadCore.util.PluginUtils;
import liusheng.downloadInterface.*;

import java.util.List;
import java.util.stream.Collectors;

public class PageControllerHBox extends HBox {
    private JFXComboBox<SearchLabel> comboBox;
    private JFXButton pageButton;
    private JFXTextField pageText;

    public JFXComboBox<SearchLabel> getComboBox() {
        return comboBox;
    }

    public JFXButton getPageButton() {
        return pageButton;
    }

    public JFXTextField getPageText() {
        return pageText;
    }


    public PageControllerHBox() {
        super();
        comboBox = new JFXComboBox<>();
        pageButton = new JFXButton("查询");
        pageText = new JFXTextField();


        pageText.setPromptText("请输出查询的内容");


        BindUtils.bind(comboBox.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(comboBox.prefWidthProperty(), this.widthProperty().multiply(0.15));

        BindUtils.bind(pageText.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(pageText.prefWidthProperty(), this.widthProperty().multiply(0.6));

        BindUtils.bind(pageButton.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(pageButton.prefWidthProperty(), this.widthProperty().multiply(0.15));

        HBox.setMargin(comboBox, new Insets(0, 30, 0, 30));
        HBox.setMargin(pageText, new Insets(0, 10, 0, 10));
        this.getChildren().addAll(comboBox, pageText, pageButton);

        this.setAlignment(Pos.CENTER_LEFT);
    }

    public void init() {

        List<PagePluginHolder> ps = PluginUtils.getPagePlugins()
                .stream().map(PagePlugin::get).collect(Collectors.toList());

        if (!ps.isEmpty()) {

            ps.forEach(searchPluginHolder -> {

                comboBox.getItems().addAll(searchPluginHolder.getShow());
            });
            comboBox.getSelectionModel().selectFirst();
            //searchButton.setOnAction(new SearchAction(comboBox, searchText, ps, searchPane, downloadPane));
        }

    }
}
