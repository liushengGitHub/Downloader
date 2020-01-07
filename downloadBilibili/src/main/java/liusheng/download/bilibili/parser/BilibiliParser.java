package liusheng.download.bilibili.parser;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import liusheng.download.bilibili.BilibiliDownloadAction;
import liusheng.download.bilibili.search.BilibiliSearchInfoParser;
import liusheng.download.bilibili.search.BilibiliSearchPageParser;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.*;
import liusheng.downloadCore.search.SearchParam;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 用来解析搜索的列表的
 */
public class BilibiliParser implements Parser<Object, Pane> {
    private final Parser infoParser = new BilibiliSearchInfoParser();
    private final SearchPageParser pageParser = new BilibiliSearchPageParser();
    private final SearchItemController itemController = new SearchItemController();


    // 返回一个Pane
    @Override
    public Pane parse(Object data) throws IOException {
        if (!(data instanceof SearchParam)) throw new IllegalArgumentException();

        SearchParam param = (SearchParam) data;

        PaginationVBox main = new PaginationVBox(itemController);
        Pagination pagination = main.getPagination();
        Map<Integer, JFXListView<AbstractSearchItemPane>> listViewMap = main.getListViewMap();
        DownloadingPane downloadingPane = param.getDownloadingPane();

        main.setAlignment(Pos.CENTER_LEFT);
        BindUtils.bind(itemController.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(itemController.prefHeightProperty(), main.heightProperty().multiply(0.15));
        BindUtils.bind(pagination.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(pagination.prefHeightProperty(), main.heightProperty().multiply(0.85));


        // 失败k可以重试3此
        ListExecutorService.commonExecutorService().execute(new FailTask(() -> {
            try {
                Object o = infoParser.parse(String.format(param.getPattern(), param.getKeyWord(), 1));
                SearchPage searchPage = pageParser.parse(o);

                List<SearchItem> items = searchPage.getItems();

                listViewMap.computeIfAbsent(0, i -> {
                    JFXListView<AbstractSearchItemPane> listView = new JFXListView<>();

                  /*  listView.setCellFactory(ls -> {
                        return new ListCell<SearchItemPane>() {

                            @Override
                            protected void updateItem(SearchItemPane item, boolean empty) {
                                super.updateItem(item, empty);

                                if (!empty && Objects.nonNull(item)) {
                                    setStyle("-fx-background-color:transparent");
                                    item. setStyle("-fx-background-color:transparent");
                                    setGraphic(item);
                                } else {
                                    setGraphic(null);
                                }
                            }
                        };
                    });*/

                    Platform.runLater(() -> {
                        setListView(listView, items, pagination, main, downloadingPane);
                    });
                    return listView;
                });

                Platform.runLater(() -> {
                    pagination.setPageCount(searchPage.getPages());
                    pagination.setPageFactory(index -> {

                        JFXListView<AbstractSearchItemPane> listView = listViewMap.get(index);

                        if (Objects.nonNull(listView)) return listView;
                        listView = new JFXListView<>();
                        BindUtils.bind(listView.prefWidthProperty(), pagination.widthProperty());
                        BindUtils.bind(listView.prefHeightProperty(), pagination.heightProperty().subtract(50));
                        listViewMap.put(index, listView);

                        JFXListView<AbstractSearchItemPane> finalListView = listView;
                        ListExecutorService.commonExecutorService().execute(new FailTask(() -> {
                            String format = String.format(param.getPattern(), param.getKeyWord(), index + 1);
                            try {
                                Object o1 = infoParser.parse(format);
                                SearchPage searchPage1 = pageParser.parse(o1);
                                List<SearchItem> items1 = searchPage1.getItems();
                                if (items1 != null && !items1.isEmpty()) {
                                    Platform.runLater(() -> {
                                        setListView(finalListView, items1, pagination, main, downloadingPane);
                                    });
                                } else {
                                    Platform.runLater(() -> {
                                        pagination.setPageCount(index + 1);
                                    });
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }));
                        return listView;
                    });
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        return main;
    }

    private void setListView(JFXListView<AbstractSearchItemPane> listView, List<SearchItem> items, Pagination pagination, PaginationVBox paginationVBox, DownloadingPane pane) {
        List<SearchDownloadItemPane> searchDownloadItemPanes = IntStream.range(0, items.size()).boxed()
                .map(i -> {
                    SearchItem item = items.get(i);
                    SearchDownloadItemPane searchDownloadItemPane = new SearchDownloadItemPane(i, item, pane.getDownloadingPaneContainer());
                    searchDownloadItemPane.getDownload().setOnAction(new BilibiliDownloadAction(item.getHref(), pane.getDownloadingPaneContainer()));
                    BindUtils.bind(searchDownloadItemPane.prefHeightProperty(), listView.heightProperty().multiply(0.20));
                    // 不让横向滚动条出现
                    BindUtils.bind(searchDownloadItemPane.prefWidthProperty(), listView.widthProperty().multiply(0.9));

                    ListExecutorService.commonExecutorService().execute(() -> {
                        String src = item.getImgSrc();
                        paginationVBox.getImageMap().computeIfAbsent(src, s -> {
                            Image image = new Image(src, 100, 30, true, true);
                            Platform.runLater(() -> {
                                searchDownloadItemPane.getImageView().setImage(image);
                            });
                            return image;
                        });

                    });
                    return searchDownloadItemPane;
                }).collect(Collectors.toList());

        listView.getItems().addAll(searchDownloadItemPanes);
        BindUtils.bind(listView.prefWidthProperty(), pagination.widthProperty());
        BindUtils.bind(listView.prefHeightProperty(), pagination.heightProperty().subtract(50));
    }
}
