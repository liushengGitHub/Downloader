package liusheng.downloadCore;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.pane.*;
import liusheng.downloadCore.search.SearchParam;
import liusheng.downloadCore.util.BindUtils;
import liusheng.downloadCore.util.StringUtils;
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

public abstract class AbstractSearchPaneParser implements Parser<Object, Pane> {
    private  Parser infoParser;
    private  SearchPageParser pageParser ;
    private final SearchItemController itemController = new SearchItemController();


    public Parser getInfoParser() {
        return Objects.isNull(infoParser) ? (infoParser=getInfoParser0()) : infoParser;
    }

    public SearchPageParser getPageParser() {
        return Objects.isNull(pageParser) ? (pageParser= getPageParser0()) : pageParser;
    }


    protected abstract Parser getInfoParser0();
    protected  abstract SearchPageParser getPageParser0();

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
                Object o = getInfoParser().parse(String.format(param.getPattern(), param.getKeyWord(), 1));
                SearchPage searchPage = getPageParser().parse(o);

                List<SearchItem> items = searchPage.getItems();

                listViewMap.computeIfAbsent(0, i -> {
                    JFXListView<AbstractSearchItemPane> listView = new JFXListView<>();

                    if (items != null && !items.isEmpty()) {
                        Platform.runLater(() -> {
                            setListView(listView, items, pagination, main, downloadingPane);

                        });
                    }
                    Platform.runLater(() -> {
                        //pagination.setPageCount(2);
                        pagination.setCurrentPageIndex(0);
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
                                Object o1 = getInfoParser().parse(format);
                                SearchPage searchPage1 = getPageParser().parse(o1);
                                List<SearchItem> items1 = searchPage1.getItems();
                                if (items1 != null && !items1.isEmpty()) {
                                    Platform.runLater(() -> {
                                        setListView(finalListView, items1, pagination, main, downloadingPane);
                                        pagination.setPageCount(index + 2);

                                    });
                                }
                                Platform.runLater(() -> {
                                    pagination.setPageCount(index + 2);
                                    pagination.setCurrentPageIndex((index ));
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

    private void setListView(JFXListView<AbstractSearchItemPane> listView, List<SearchItem> items, Pagination pagination, PaginationVBox paginationVBox, DownloadingPane pane) {
        List<AbstractSearchItemPane> searchDownloadItemPanes = IntStream.range(0, items.size()).boxed()
                .map(i -> {
                    SearchItem item = items.get(i);
                    AbstractSearchItemPane searchDownloadItemPane =
                            getSearchItemPane(pane, i, item);

                    BindUtils.bind(searchDownloadItemPane.prefHeightProperty(), listView.heightProperty().multiply(0.20));
                    // 不让横向滚动条出现
                    BindUtils.bind(searchDownloadItemPane.prefWidthProperty(), listView.widthProperty().multiply(0.9));

                    if (!StringUtils.isEmpty(item.getImgSrc())) {
                        ListExecutorService.commonExecutorService().execute(() -> {
                            String src = item.getImgSrc();
                            paginationVBox.getImageMap().computeIfAbsent(src, s -> {
                                Image image = new Image(StringUtils.urlProcess(src), 100, 30, true, true);
                                Platform.runLater(() -> {
                                    searchDownloadItemPane.getImageView().setImage(image);
                                });
                                return image;
                            });

                        });
                    }
                    return searchDownloadItemPane;
                }).collect(Collectors.toList());

        listView.getItems().addAll(searchDownloadItemPanes);
        BindUtils.bind(listView.prefWidthProperty(), pagination.widthProperty());
        BindUtils.bind(listView.prefHeightProperty(), pagination.heightProperty().subtract(50));
    }

    protected  abstract AbstractSearchItemPane getSearchItemPane(DownloadingPane pane, Integer i, SearchItem item);
}
