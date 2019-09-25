package liusheng.download.bilibili.parser;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import liusheng.download.bilibili.pane.PaginationVBox;
import liusheng.download.bilibili.pane.SearchItemController;
import liusheng.download.bilibili.pane.SearchItemPane;
import liusheng.download.bilibili.search.BilibiliSearchInfoParser;
import liusheng.download.bilibili.search.BilibiliSearchPageParser;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DownloadingPane;
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
        Map<Integer, JFXListView<SearchItemPane>> listViewMap = main.getListViewMap();
        DownloadingPane downloadingPane = param.getDownloadingPane();

        main.setAlignment(Pos.CENTER_LEFT);
        BindUtils.bind(itemController.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(itemController.prefHeightProperty(), main.heightProperty().multiply(0.15));
        BindUtils.bind(pagination.prefWidthProperty(), main.widthProperty());
        BindUtils.bind(pagination.prefHeightProperty(), main.heightProperty().multiply(0.85));


        // 失败k可以重试3此
        FailListExecutorService.commonExecutorService().execute(new FailTask(() -> {
            try {
                Object o = infoParser.parse(String.format(param.getPattern(), param.getKeyWord(), 1));
                SearchPage searchPage = pageParser.parse(o);

                List<SearchItem> items = searchPage.getItems();

                listViewMap.computeIfAbsent(0, i -> {
                    JFXListView<SearchItemPane> listView = new JFXListView<>();

                    Platform.runLater(() -> {
                        setListView(listView, items, pagination, main, downloadingPane);
                    });
                    return listView;
                });

                Platform.runLater(() -> {
                    pagination.setPageCount(searchPage.getPages());

                    pagination.setPageFactory(index -> {

                        JFXListView<SearchItemPane> listView = listViewMap.get(index);

                        if (Objects.nonNull(listView)) return listView;
                        listView = new JFXListView<>();
                        BindUtils.bind(listView.prefWidthProperty(), pagination.widthProperty());
                        BindUtils.bind(listView.prefHeightProperty(), pagination.heightProperty().subtract(50));
                        listViewMap.put(index, listView);

                        JFXListView<SearchItemPane> finalListView = listView;
                        FailListExecutorService.commonExecutorService().execute(new FailTask(() -> {
                            String format = String.format(param.getPattern(), param.getKeyWord(), index + 1);
                            try {
                                Object o1 = infoParser.parse(format);
                                SearchPage searchPage1 = pageParser.parse(o1);
                                Platform.runLater(() -> {
                                    setListView(finalListView, searchPage1.getItems(), pagination, main, downloadingPane);
                                });
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

    private void setListView(JFXListView<SearchItemPane> listView, List<SearchItem> items, Pagination pagination, PaginationVBox paginationVBox, DownloadingPane pane) {
        List<SearchItemPane> searchItemPanes = IntStream.range(0, items.size()).boxed()
                .map(i -> {
                    SearchItem item = items.get(i);
                    SearchItemPane searchItemPane = new SearchItemPane(i, item, pane.getDownloadingPaneContainer());

                    BindUtils.bind(searchItemPane.prefHeightProperty(), listView.heightProperty().multiply(0.20));
                    // 不让横向滚动条出现
                    BindUtils.bind(searchItemPane.prefWidthProperty(), listView.widthProperty().multiply(0.9));

                    FailListExecutorService.commonExecutorService().execute(() -> {
                        String src = item.getImgSrc();
                        paginationVBox.getImageMap().computeIfAbsent(src, s -> {
                            Image image = new Image(src, 100, 30, true, true);
                            Platform.runLater(() -> {
                                searchItemPane.getImageView().setImage(image);
                            });
                            return image;
                        });

                    });
                    return searchItemPane;
                }).collect(Collectors.toList());

        listView.getItems().addAll(searchItemPanes);
        BindUtils.bind(listView.prefWidthProperty(), pagination.widthProperty());
        BindUtils.bind(listView.prefHeightProperty(), pagination.heightProperty().subtract(50));
    }
}
