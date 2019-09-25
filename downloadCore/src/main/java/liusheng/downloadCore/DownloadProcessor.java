package liusheng.downloadCore;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.Label;
import liusheng.downloadCore.pane.DownloadListDialogItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;

public interface DownloadProcessor {
    void processor( JFXComboBox<Label> quality, JFXListView<DownloadListDialogItemPane> listView, DownloadingPaneContainer downloadingPaneContainer);
    default void before(JFXListView<DownloadListDialogItemPane> listView){}
    default void after( JFXComboBox<Label> quality, JFXListView<DownloadListDialogItemPane> listView, DownloadingPaneContainer downloadingPaneContainer){}
}
