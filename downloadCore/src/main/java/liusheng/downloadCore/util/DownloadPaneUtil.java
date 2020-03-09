package liusheng.downloadCore.util;

import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.entity.DownloadItemPaneEntity;
import liusheng.downloadCore.pane.DownloadItemPane;

/**
 * 年: 2020  月: 01 日: 08 小时: 22 分钟: 12
 * 用户名: LiuSheng
 */

public class DownloadPaneUtil {
    public static void removeListItem(AbstractDataBean abstractDataBean){
        DownloadItemPane itemPane = (DownloadItemPane) abstractDataBean.getPane();
        JFXListView<DownloadItemPaneEntity> listView = itemPane.getListView();
        ObservableList<DownloadItemPaneEntity> items = listView.getItems();
        int i = items.indexOf(itemPane.getEntity());
        if (i != -1) {
            abstractDataBean.getDownloadPane().getDownloadedPane().getDownloadedPaneContainer().getListView().getItems().add(items.get(i));
            items.remove(i);
        }
    }
}
