package liusheng.download.bilibili.entity;

import liusheng.downloadCore.entity.AbstractVideoBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;

public class AbstractVideoBeanHolder {
    private AbstractVideoBean abstractVideoBean;
    // 文件名字 ，不包含后缀
    private String name;
    private String refererUrl;
    private DownloadItemPaneEntity downloadItemPaneEntity;

    public DownloadItemPaneEntity getDownloadItemPaneEntity() {
        return downloadItemPaneEntity;
    }

    public AbstractVideoBeanHolder(AbstractVideoBean abstractVideoBean, String name, String refererUrl, DownloadItemPaneEntity downloadItemPaneEntity) {
        this.abstractVideoBean = abstractVideoBean;
        this.name = name;
        this.refererUrl = refererUrl;
        this.downloadItemPaneEntity = downloadItemPaneEntity;
    }

    public AbstractVideoBean getAbstractVideoBean() {
        return abstractVideoBean;
    }

    public String getName() {
        return name;
    }

    public String getRefererUrl() {
        return refererUrl;
    }
}
