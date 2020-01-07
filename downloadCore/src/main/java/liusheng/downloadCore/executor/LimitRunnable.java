package liusheng.downloadCore.executor;

import javafx.application.Platform;
import javafx.scene.Node;
import liusheng.downloadCore.RunnableInfo;
import liusheng.downloadCore.pane.DownloadItemPane;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * 年: 2019  月: 12 日: 25 小时: 16 分钟: 56
 * 用户名: LiuSheng
 */

public class LimitRunnable implements Runnable {
    private final RunnableInfo runnableInfo;
    private final Runnable runnable;

    private Logger logger = Logger.getLogger(LimitRunnable.class);

    public LimitRunnable(RunnableInfo runnableInfo, Runnable runnable) {
        this.runnableInfo = runnableInfo;
        this.runnable = runnable;
    }

    @Override
    public void run() {

        doRunnable();
    }

    private void doRunnable() {
        // 任务加1
        try {
            logger.info("开始下载 : " + runnableInfo.getRefererUrl());
            runnable.run();
            logger.info("下载成功 : " + runnableInfo.getRefererUrl());
        } catch (Throwable throwable) {
            Node node = runnableInfo.getPane();
            if (Objects.isNull(node) || !(node instanceof DownloadItemPane)) throw new RuntimeException(throwable);
            DownloadItemPane itemPane = (DownloadItemPane) node;

            Platform.runLater(() -> {
                itemPane.getStateLabel().setText("下载失败..");
            });
            logger.info("下载失败",throwable);
            logger.info("下载失败 : " + runnableInfo.getRefererUrl() + " Exception : " + throwable);
        } finally {
            // 任务减1
            ListExecutorService.getCurrentTaskNumber().getAndDecrement();
        }
    }
}
