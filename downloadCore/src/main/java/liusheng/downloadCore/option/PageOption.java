package liusheng.downloadCore.option;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import liusheng.downloadCore.Option;
import liusheng.downloadCore.pane.DownloadPane;
import liusheng.downloadCore.pane.page.PagePane;
import liusheng.downloadCore.search.SearchPane;

public class PageOption implements Option {
    private JFXButton button = new JFXButton("页面");

    private PagePane pagePane = new PagePane();
    @Override
    public JFXButton button() {

        return button;
    }

    @Override
    public Pane pane() {
        return pagePane;
    }
}
