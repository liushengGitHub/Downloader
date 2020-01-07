package liusheng.downloadCore.pane;

import com.jfoenix.controls.JFXListView;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每次搜索都会创建
 */
public class PaginationVBox extends VBox {
    private final Pagination pagination = new Pagination(10);
    private final SearchItemController searchItemController;
    private final Map<Integer, JFXListView<AbstractSearchItemPane>> listViewMap = new HashMap<>();
    private final Map<String, Image> imageMap = new ConcurrentHashMap<>();

    public Map<String, Image> getImageMap() {
        return imageMap;
    }

    public Map<Integer, JFXListView<AbstractSearchItemPane>> getListViewMap() {
        return listViewMap;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public SearchItemController getSearchItemController() {
        return searchItemController;
    }

    public PaginationVBox(SearchItemController searchItemController) {
        super();

        this.searchItemController = searchItemController;
        this.getChildren().addAll(searchItemController, pagination);
    }
}
