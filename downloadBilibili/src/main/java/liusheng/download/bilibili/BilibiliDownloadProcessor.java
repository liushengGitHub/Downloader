package liusheng.download.bilibili;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import liusheng.download.bilibili.entity.*;
import liusheng.downloadCore.*;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DownloadListDialogItemPane;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.StringUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BilibiliDownloadProcessor implements DownloadProcessor {

    private final List<AllPageBean> pageBeanList;
    private final PagesBean pagesBean;
    private final Logger logger = Logger.getLogger(BilibiliDownloadProcessor.class);
    private final SubPageDownload pageDownload;
    private final List<Integer> qualityList = Arrays.asList(16, 32, 48, 64, 80);

    public SubPageDownload getPageDownload() {
        return pageDownload;
    }

    public BilibiliDownloadProcessor(List<AllPageBean> pageBeanList, PagesBean pagesBean, SubPageDownload pageDownload) {
        this.pageBeanList = pageBeanList;
        this.pagesBean = pagesBean;
        this.pageDownload = pageDownload;
    }

    @Override
    public void before(JFXListView<DownloadListDialogItemPane> listView) {
        List<DownloadListDialogItemPane> list = pageBeanList.stream().map(allPageBean -> {
            int page = allPageBean.getPage();
            String part = allPageBean.getPart();
            return new DownloadListDialogItemPane(String.valueOf(page), part);
        }).collect(Collectors.toList());
        listView.getItems().addAll(list);
    }

    /**
     * 点击确定按钮 执行的事件
     *
     * @param quality
     * @param listView
     * @param downloadingPaneContainer
     */
    @Override
    public void processor(JFXComboBox<Label> quality,
                          JFXListView<DownloadListDialogItemPane> listView, DownloadingPaneContainer downloadingPaneContainer) {


        SingleSelectionModel<Label> selectionModel = quality.getSelectionModel();

        int size = quality.getItems().size();

        int qualityIndex = IntStream.range(0, size).filter(selectionModel::isSelected).findFirst().orElse(size - 1);

        ObservableList<DownloadListDialogItemPane> items = listView.getItems();

        List<AllPageBean> selectAllPageBean = IntStream.range(0, items.size()).filter(index -> items.get(index).getCheckBox().isSelected())
                .boxed().map(pageBeanList::get).collect(Collectors.toList());
        // 这个视频的名字
        String videoName = Optional.ofNullable(pagesBean).map(p -> p.getVideoData()).map(videoDataBean -> StringUtils.isEmpty(videoDataBean.getTitle())
                ? UUID.randomUUID().toString() : StringUtils.fileNameHandle(videoDataBean.getTitle())).get();
        ListExecutorService.commonExecutorService().execute(new FailTask(() -> {

            IntStream.range(0, selectAllPageBean.size()).forEach(selectAllPageBeanIndex -> {

                AllPageBean page = selectAllPageBean.get(selectAllPageBeanIndex);
                try {
                    int index = page.getPage();

                    // 文件名字 ，不包含后缀
                    String name = index + "_" + page.getPart();

                    // 每一p的 url
                    String vUrl = pagesBean.getUrl() + "?p=" + index;
                    DownloadList downloadList = DownloadList.downloadList();
                    List<String> unDownloadList = downloadList.getUnDownloadList();
                    List<String> downloadedList = downloadList.getDownloadedList();
                    synchronized (downloadList) {
                        if (unDownloadList.contains(vUrl) || downloadedList.contains(vUrl)) {
                            Platform.runLater(() -> {
                                new Alert(Alert.AlertType.INFORMATION, "已经在下载列表中了").show();
                            });
                            return;
                        }
                        unDownloadList.add(vUrl);
                    }
                    //添加到为下载的列表中
                    pageDownload.download(vUrl, qualityList.size() <= qualityIndex ? 0 : qualityList.get(qualityIndex),  name,videoName);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });

        }));
    }


}
/*
*

                } catch (IOException ex) {
                    throw new RuntimeException();
                }*/