package liusheng.download.bilibilispace.action;

import javafx.event.ActionEvent;
import liusheng.download.bilibili.BilibiliDownloadAction;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.pane.DownloadingPaneContainer;

/**
 * 年: 2019  月: 12 日: 31 小时: 14 分钟: 20
 * 用户名: LiuSheng
 */

public class BilibiliSpaceDownloadAction extends AbstractDownloadAction {
    public BilibiliSpaceDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {
        super(href, downloadingPaneContainer);
    }

    @Override
    public void handle(ActionEvent event) {

        new BilibiliDownloadAction(href,downloadingPaneContainer).handle(event);

    }

    @Override
    protected void asynHandle(ActionEvent event) {

    }
}
