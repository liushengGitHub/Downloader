package liusheng.downloadCore.option;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import liusheng.downloadCore.Option;
import liusheng.downloadCore.search.SearchPane;

public class SearchOption implements Option {

    private JFXButton button = new JFXButton("搜索");
    private SearchPane searchPane = new SearchPane();

    @Override
    public JFXButton button() {

        return button;
    }

    @Override
    public Pane pane() {
        return searchPane;
    }
}
