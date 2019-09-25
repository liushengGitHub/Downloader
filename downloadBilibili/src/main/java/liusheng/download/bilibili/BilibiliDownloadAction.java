package liusheng.download.bilibili;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import liusheng.download.bilibili.entity.AllPageBean;
import liusheng.download.bilibili.entity.PagesBean;
import liusheng.download.bilibili.parser.PageInfoParser;
import liusheng.downloadCore.pane.DownloadListDialog;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;


public class BilibiliDownloadAction implements EventHandler<ActionEvent> {
    private final PageInfoParser pageInfoParser = new PageInfoParser();
    private String url;
    private DownloadingPaneContainer downloadingPaneContainer;
    public static Semaphore semaphore = new Semaphore(2);

    public BilibiliDownloadAction(String url, DownloadingPaneContainer downloadingPaneContainer) {
        this.url = url;
        this.downloadingPaneContainer = downloadingPaneContainer;
    }

    @Override
    public void handle(ActionEvent event) {
        FailListExecutorService.commonExecutorService()
                .execute(new FailTask(() -> {

                    try {
                        PagesBean pages = pageInfoParser.parse(url);
                        if (pages == null) return;
                        // referer
                        pages.setUrl(url);

                        //PagesBean pages = pagesBean;
                        // 这个视频的所有分页视频
                        List<AllPageBean> pageBeanList = Optional.ofNullable(pages)
                                .map(p -> p.getVideoData()).map(videoDataBean -> videoDataBean.getPages()).orElse(Collections.emptyList());

                        // 为null 说明没有这个视频
                        if (pageBeanList.isEmpty()) return;

                        // 这个视频的名字
                           /* String videoName = Optional.ofNullable(pages).map(p -> p.getVideoData()).map(videoDataBean -> StringUtils.isEmpty(videoDataBean.getTitle())
                                    ? UUID.randomUUID().toString() : StringUtils.fileNameHandle(videoDataBean.getTitle())).get();
                            JFXListView<Label> listView = new JFXListView<>();*/

                        Platform.runLater(() -> {
                            DownloadListDialog listDialog = new DownloadListDialog(downloadingPaneContainer,
                                    new BilibiliDownloadProcessor(pageBeanList, pages, semaphore, new BilibiliPageDownload(downloadingPaneContainer, semaphore, pages)));

                            listDialog.showAndWait();
                        });

                    } catch (IOException e) {
                        throw new RuntimeException();
                    }

                }));

    }
}
