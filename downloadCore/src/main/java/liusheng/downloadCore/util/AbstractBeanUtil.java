package liusheng.downloadCore.util;

import com.jfoenix.controls.JFXListView;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.pane.DownloadItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 年: 2019  月: 12 日: 25 小时: 17 分钟: 22
 * 用户名: LiuSheng
 */

public class AbstractBeanUtil {
    private AbstractBeanUtil(){}

    public static void setAbstractProperty(DownloadingPaneContainer downloadingPaneContainer, File  dirFile, JFXListView<DownloadItemPaneEntity> listView1,
                                           AbstractDataBean abstractDataBean, String name, String referUrl, DownloadItemPane downloadItemPane, int quality, DownloadItemPaneEntity e1) {
        abstractDataBean.setName(StringUtils.fileNameHandle(name));
        abstractDataBean.setDirFile(dirFile);
        abstractDataBean.setRefererUrl(referUrl);
        abstractDataBean.setSize(new AtomicLong());
        abstractDataBean.setAllSize(new AtomicLong());
        abstractDataBean.setDownloadPane(downloadingPaneContainer.getDownloadPane());
        abstractDataBean.setPane(downloadItemPane);
        abstractDataBean.setQuality(quality);
        downloadItemPane.setListView(listView1);
        downloadItemPane.setEntity(e1);
        e1.setAbstractDataBean(abstractDataBean);
    }
}
