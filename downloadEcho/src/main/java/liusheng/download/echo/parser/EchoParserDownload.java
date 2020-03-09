package liusheng.download.echo.parser;

import com.jfoenix.controls.JFXAlert;
import javafx.application.Platform;
import liusheng.download.echo.action.EchoDownloadAction;
import liusheng.download.echo.entity.EchoEntity;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.AbstractDownloadSearchParser;
import liusheng.downloadCore.executor.ListExecutorService;
import liusheng.downloadCore.pane.*;
import liusheng.downloadCore.player.CustomeMediaPlayer;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPageParser;

import java.io.IOException;
import java.util.Optional;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 02
 * 用户名: LiuSheng
 */

public class EchoParserDownload extends AbstractDownloadSearchParser {
    protected AbstractDownloadAction getDownloadHandler0(String href, DownloadingPaneContainer downloadingPaneContainer) {
        return new EchoDownloadAction(href, downloadingPaneContainer);
    }

    protected Parser getInfoParser0() {
        return new EchoInfoParser();
    }

    protected SearchPageParser getPageParser0() {
        return new EchoSearchPageParser();
    }

    @Override
    protected AbstractSearchItemPane getSearchItemPane(DownloadingPane pane, Integer i, SearchItem item) {
        SearchMusicItemPane pane1 = new SearchMusicItemPane(i, item, pane.getDownloadingPaneContainer());
        pane1.getDownload().setOnAction(getDownloadHandler0(item.getHref(), pane.getDownloadingPaneContainer()));
        pane1.getPlay().setOnAction(e -> {
            initPlayPane(item);
        });
        return pane1;
    }

    private void initPlayPane(SearchItem item) {
        JFXAlert<Object> player = new JFXAlert<>();
        player.setSize(600, 480);
        player.setResizable(false);
        MusicPlayerPane playerPane = new MusicPlayerPane();
        ListExecutorService.commonExecutorService()
                .execute(() -> {

                    try {
                      /*  EchoInfoParser echoInfoParser = new EchoInfoParser();
                        EchoEntity echoEntity = echoInfoParser.parse(item.getHref());
                        String playUrl = Optional.ofNullable(echoEntity.getData()).filter(list-> list.size() > 0).map(list-> list.stream().map(dataBean -> dataBean.getSource())
                        .filter(source-> !StringUtils.isEmpty(source)).findFirst().orElse(null)).orElseThrow(RuntimeException::new);


                        player.setTitle( Optional.ofNullable(echoEntity.getData()).filter(list-> list.size() > 0).map(list-> list.stream().map(dataBean -> dataBean.getInfo())
                                .filter(source-> !StringUtils.isEmpty(source)).findFirst().orElse(null)).orElseThrow(RuntimeException::new));*/

                        Platform.runLater(()->{
                            player.setTitle(item.getTitle());
                        });
                        CustomeMediaPlayer customeMediaPlayer = new CustomeMediaPlayer(item.getHref(), playerPane, player);
                        customeMediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        player.close();
                    }

                });
        player.setContent(playerPane);
        player.show();
    }

}
