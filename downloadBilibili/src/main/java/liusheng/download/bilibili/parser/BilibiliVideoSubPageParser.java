package liusheng.download.bilibili.parser;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import liusheng.download.bilibili.BilibiliDownloadAction;
import liusheng.download.bilibili.BilibiliDownloadProcessor;
import liusheng.download.bilibili.BilibiliPageDownload;
import liusheng.download.bilibili.entity.AllPageBean;
import liusheng.download.bilibili.entity.PagesBean;
import liusheng.downloadCore.executor.FailListExecutorService;
import liusheng.downloadCore.executor.FailTask;
import liusheng.downloadCore.pane.DownloadListDialogItemPane;
import liusheng.downloadCore.pane.DownloadSelectPane;
import liusheng.downloadCore.pane.DownloadingPane;
import liusheng.downloadCore.search.SearchParam;
import liusheng.downloadInterface.Parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BilibiliVideoSubPageParser implements Parser<Object, Pane> {
    private final PageInfoParser pageInfoParser = new PageInfoParser();

    @Override
    public Pane parse(Object param) throws IOException {
        DownloadSelectPane downloadSelectPane = new DownloadSelectPane();
        FailListExecutorService.commonExecutorService().execute(new FailTask(() -> {
            try {
                Semaphore semaphore = BilibiliDownloadAction.semaphore;
                /**
                 *
                 * 单页所有p 的信息
                 */
                SearchParam searchParam = (SearchParam) param;
                String url = String.format(searchParam.getPattern(), searchParam.getKeyWord());
                DownloadingPane downloadingPane = searchParam.getDownloadingPane();
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

                BilibiliDownloadProcessor downloadProcessor = new BilibiliDownloadProcessor(pageBeanList, pages, semaphore,
                        new BilibiliPageDownload(downloadingPane.getDownloadingPaneContainer(), semaphore, pages));
                downloadProcessor.before(downloadSelectPane.getListView());

                downloadSelectPane.getDownloadButton().setOnAction(e -> {
                    downloadProcessor.processor(downloadSelectPane.getQuality(), downloadSelectPane.getListView(), downloadingPane.getDownloadingPaneContainer());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        return downloadSelectPane;
    }
}
