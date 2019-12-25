package liusheng.downloadCore.option;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import liusheng.downloadCore.Option;
import liusheng.downloadCore.pane.DownloadPane;
import liusheng.downloadCore.search.SearchPane;

public class DownloadOption implements Option {
    private JFXButton jfxButton = new JFXButton("下载");
    private DownloadPane downloadPane = new DownloadPane();

    @Override
    public JFXButton button() {
        return jfxButton;
    }
    @Override
    public Pane pane() {
        return downloadPane;
    }
}
