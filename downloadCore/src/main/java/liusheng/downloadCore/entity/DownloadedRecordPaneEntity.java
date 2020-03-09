package liusheng.downloadCore.entity;

import liusheng.downloadCore.pane.DownloadedItemPane;

/**
 * 年: 2020  月: 01 日: 08 小时: 21 分钟: 58
 * 用户名: LiuSheng
 */

public class DownloadedRecordPaneEntity  {
    private AbstractDataBean abstractDataBean;
    private DownloadedItemPane downloadedItemPane;

    public AbstractDataBean getAbstractDataBean() {
        return abstractDataBean;
    }

    public void setAbstractDataBean(AbstractDataBean abstractDataBean) {
        this.abstractDataBean = abstractDataBean;
    }

    public DownloadedItemPane getDownloadedItemPane() {
        return downloadedItemPane;
    }

    public void setDownloadedItemPane(DownloadedItemPane downloadedItemPane) {
        this.downloadedItemPane = downloadedItemPane;
    }
}
