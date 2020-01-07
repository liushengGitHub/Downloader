package liusheng.downloadCore;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import org.apache.log4j.Logger;

/**
 * 年: 2019  月: 12 日: 27 小时: 19 分钟: 39
 * 用户名: LiuSheng
 */

public abstract class AbstractDownloadAction implements EventHandler<ActionEvent> {

    protected  final String href;
    protected  final DownloadingPaneContainer downloadingPaneContainer;
    private final Logger logger = Logger.getLogger(getClass());
    public AbstractDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {
        this.href = href;
        this.downloadingPaneContainer = downloadingPaneContainer;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            ListExecutorService.commonExecutorService().execute(() -> {
                asynHandle(event);
            });
        }catch (Throwable e) {
            logger.info("error",e);
        }
    }

    protected abstract void asynHandle(ActionEvent event) ;
}
