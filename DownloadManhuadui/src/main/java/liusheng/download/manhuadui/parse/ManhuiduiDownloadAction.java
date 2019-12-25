package liusheng.download.manhuadui.parse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import liusheng.download.manhuadui.donwload.CartoonDonwloader;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DownloadListDialog;
import liusheng.downloadCore.pane.DownloadingPaneContainer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class ManhuiduiDownloadAction implements EventHandler<ActionEvent> {
    private String url;
    private DownloadingPaneContainer downloadingPaneContainer;
    public ManhuiduiDownloadAction(String url, DownloadingPaneContainer downloadingPaneContainer) {
        this.url = url;
        this.downloadingPaneContainer = downloadingPaneContainer;
    }

    @Override
    public void handle(ActionEvent event) {
        FailListExecutorService.commonExecutorService()
                .execute(new FailTask(() -> {
                    new CartoonDonwloader("cartoon").download(url);
                }));

    }
}
