package liusheng.downloadCore.pane.page;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import liusheng.downloadCore.pane.DownloadPane;
import liusheng.downloadCore.search.SearchParam;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadInterface.PagePluginHolder;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchLabel;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class PageAction implements EventHandler<ActionEvent> {
    private final JFXComboBox<SearchLabel> comboBox;
    private final JFXTextField searchText;
    private final List<PagePluginHolder> pagePluginHolders;
    private PagePane pagePane;
    private DownloadPane downloadPane;

    public PageAction(JFXComboBox<SearchLabel> comboBox, JFXTextField searchText, List<PagePluginHolder> pagePluginHolders, PagePane pagePane, DownloadPane downloadPane) {
        this.comboBox = comboBox;
        this.searchText = searchText;
        this.pagePluginHolders = pagePluginHolders;
        this.pagePane = pagePane;
        this.downloadPane = downloadPane;
    }


    final
    @Override
    public void handle(ActionEvent event) {
        String text = searchText.getText();
        if (StrUtil.isEmpty(text)) return;

        SingleSelectionModel<SearchLabel> selectionModel = comboBox.getSelectionModel();
        ObservableList<SearchLabel> items = comboBox.getItems();
        int size = items.size();
        int index = IntStream.range(0, size).filter(selectionModel::isSelected).findFirst().orElse(0);
        if (size > 0) {

            PagePluginHolder holder = pagePluginHolders.get(index);
            String pattern = holder.getShow().getPattern();
            Parser<Object, Pane> parser = holder.getParser();


            try {
                Pane pane = parser.parse(new SearchParam(pattern, text, downloadPane.getDownloadingPane()));

                ObservableList<Node> list = pagePane.getChildren();

                int size1 = list.size();
                if (size1 > 2) {
                    list.remove(2, size1);
                }

                VBox containerPane = pagePane.getContainerPane();



                BindUtils.bind(pane.prefWidthProperty(), containerPane.widthProperty());
                BindUtils.bind(pane.prefHeightProperty(), containerPane.heightProperty());

                ObservableList<Node> children = containerPane.getChildren();


                children.clear();
                children.addAll(pane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
