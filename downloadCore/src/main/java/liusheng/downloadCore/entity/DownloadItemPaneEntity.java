package liusheng.downloadCore.entity;

import liusheng.downloadCore.pane.DownloadItemPane;

public class DownloadItemPaneEntity {
    private AbstractDataBean abstractDataBean;
    private int quality;

    public AbstractDataBean getAbstractDataBean() {
        return abstractDataBean;
    }

    public int getQuality() {
        return quality;
    }
    private DownloadItemPane downloadItemPane;

    public DownloadItemPane getDownloadItemPane() {
        return downloadItemPane;
    }

    public void setAbstractDataBean(AbstractDataBean abstractDataBean) {
        this.abstractDataBean = abstractDataBean;
    }

    public DownloadItemPaneEntity(int quality, DownloadItemPane downloadItemPane) {

        this.quality = quality;
        this.downloadItemPane = downloadItemPane;
    }
}
