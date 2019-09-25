package liusheng.downloadCore.entity;

import liusheng.downloadCore.pane.DownloadItemPane;

public class DownloadItemPaneEntity {
    private AbstractVideoBean abstractVideoBean;
    private int quality;

    public AbstractVideoBean getAbstractVideoBean() {
        return abstractVideoBean;
    }

    public int getQuality() {
        return quality;
    }
    private DownloadItemPane downloadItemPane;

    public DownloadItemPane getDownloadItemPane() {
        return downloadItemPane;
    }

    public void setAbstractVideoBean(AbstractVideoBean abstractVideoBean) {
        this.abstractVideoBean = abstractVideoBean;
    }

    public DownloadItemPaneEntity(int quality, DownloadItemPane downloadItemPane) {

        this.quality = quality;
        this.downloadItemPane = downloadItemPane;
    }
}
