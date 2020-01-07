package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import liusheng.downloadInterface.SearchItem;

/**
 * 年: 2020  月: 01 日: 06 小时: 22 分钟: 35
 * 用户名: LiuSheng
 */

public class SearchMusicItemPane extends AbstractSearchItemPane {
    private final JFXButton download;
    private final JFXButton play;

    public SearchMusicItemPane(int index, SearchItem item, DownloadingPaneContainer downloadingPaneContainer) {
        super(index, item, downloadingPaneContainer);
        play = new JFXButton("播放");
        download = new JFXButton("下载");
        download.setAlignment(Pos.CENTER);
        setSize(download, operationLabel, 0.5);
        play.setAlignment(Pos.CENTER);
        setSize(play, operationLabel, 0.5);
        operationLabel.getChildren().addAll(play, download);
    }

    public JFXButton getDownload() {
        return download;
    }

    public JFXButton getPlay() {
        return play;
    }

    @Override
    protected HBox getOperationLabel0() {
        HBox hBox = super.getOperationLabel0();


        return hBox;
    }
}
