package liusheng.download.manhuadui.parse;

import javafx.event.ActionEvent;
import liusheng.download.manhuadui.donwload.CartoonDownloader;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.pane.DownloadingPaneContainer;

public class ManhuiduiDownloadAction extends AbstractDownloadAction {
    public ManhuiduiDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {
        super(href,downloadingPaneContainer);
    }

    @Override
    protected void asynHandle(ActionEvent event) {
        new CartoonDownloader("cartoon").download(href);
    }
}
