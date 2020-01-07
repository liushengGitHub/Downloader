package liusheng.download.bilibili.entity;

import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;

public class AbstractVideoBeanHolder {
    private AbstractDataBean abstractDataBean;
    // 文件名字 ，不包含后缀
    private String name;
    private String refererUrl;
    private DownloadItemPaneEntity downloadItemPaneEntity;

    public DownloadItemPaneEntity getDownloadItemPaneEntity() {
        return downloadItemPaneEntity;
    }

    public AbstractVideoBeanHolder(AbstractDataBean abstractDataBean, String name, String refererUrl, DownloadItemPaneEntity downloadItemPaneEntity) {
        this.abstractDataBean = abstractDataBean;
        this.name = name;
        this.refererUrl = refererUrl;
        this.downloadItemPaneEntity = downloadItemPaneEntity;
    }

    public AbstractDataBean getAbstractDataBean() {
        return abstractDataBean;
    }

    public String getName() {
        return name;
    }

    public String getRefererUrl() {
        return refererUrl;
    }
}
