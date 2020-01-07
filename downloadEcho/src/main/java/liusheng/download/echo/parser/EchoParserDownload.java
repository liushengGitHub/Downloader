package liusheng.download.echo.parser;

import liusheng.download.echo.action.EchoDownloadAction;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.AbstractDownloadSearchParser;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchPageParser;

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
        return  new EchoSearchPageParser();
    }

}
