package liusheng.downloadCore.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadCore.util.FontUtils;
import liusheng.downloadCore.util.PluginUtils;
import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

import java.util.List;
import java.util.stream.Collectors;

public class SearchControllerHBox extends HBox {
    private JFXComboBox<SearchLabel> comboBox;
    private JFXButton searchButton;
    private JFXTextField searchText;

    public JFXComboBox<SearchLabel> getComboBox() {
        return comboBox;
    }

    public JFXButton getSearchButton() {
        return searchButton;
    }

    public JFXTextField getSearchText() {
        return searchText;
    }


    public SearchControllerHBox() {
        super();
        comboBox = new JFXComboBox<>();
        searchButton = new JFXButton("搜索");
        FontUtils.setFontAndColor(searchButton);

        searchText = new JFXTextField();


        searchText.setPromptText("请输出搜索的内容");


        BindUtils.bind(comboBox.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(comboBox.prefWidthProperty(), this.widthProperty().multiply(0.15));

        BindUtils.bind(searchText.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(searchText.prefWidthProperty(), this.widthProperty().multiply(0.6));

        BindUtils.bind(searchButton.prefHeightProperty(), this.heightProperty().multiply(0.8));
        BindUtils.bind(searchButton.prefWidthProperty(), this.widthProperty().multiply(0.15));

        HBox.setMargin(comboBox, new Insets(0, 30, 0, 30));
        HBox.setMargin(searchText, new Insets(0, 10, 0, 10));
        this.getChildren().addAll(comboBox, searchText, searchButton);

        this.setAlignment(Pos.CENTER_LEFT);
    }

    public void init() {
        List<SearchPluginHolder> ps = PluginUtils.getSearchPlugins()
                .stream().map(SearchPlugin::get).collect(Collectors.toList());

        if (!ps.isEmpty()) {

            ps.forEach(searchPluginHolder -> {

                comboBox.getItems().addAll(searchPluginHolder.getShow());
            });
            comboBox.getSelectionModel().selectFirst();
            //searchButton.setOnAction(new SearchAction(comboBox, searchText, ps, searchPane, downloadPane));
        }

    }
}
