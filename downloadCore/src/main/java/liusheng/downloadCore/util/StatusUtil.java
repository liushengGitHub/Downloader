package liusheng.downloadCore.util;

import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadInterface.DownloaderController;

/**
 * 年: 2020  月: 01 日: 03 小时: 20 分钟: 27
 * 用户名: LiuSheng
 */

public class StatusUtil {
    public static void retry(AbstractDataBean abstractDataBean, DownloadItemPane downloadItemPane) {
        abstractDataBean.reset();
        downloadItemPane.getStateLabel().setText("正在等待");
        downloadItemPane.getLocal().setState(DownloaderController.EXECUTE);
    }

    public static void download( DownloadItemPane downloadItemPane) {
        downloadItemPane.getStateLabel().setText("正在下载");
        downloadItemPane.getLocal().setState(DownloaderController.EXECUTE);
    }

    public static void exception( DownloadItemPane downloadItemPane) {
        downloadItemPane.getStateLabel().setText("下载失败");
        downloadItemPane.getLocal().setState(DownloaderController.EXCEPTION);
    }
}
