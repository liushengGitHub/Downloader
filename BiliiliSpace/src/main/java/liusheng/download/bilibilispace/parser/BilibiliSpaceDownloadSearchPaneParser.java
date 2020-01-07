package liusheng.download.bilibilispace.parser;

import liusheng.download.bilibilispace.action.BilibiliSpaceDownloadAction;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.AbstractDownloadSearchParser;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchPageParser;

/**
 * 年: 2019  月: 12 日: 31 小时: 14 分钟: 14
 * 用户名: LiuSheng
 */

public class BilibiliSpaceDownloadSearchPaneParser extends AbstractDownloadSearchParser {
    protected AbstractDownloadAction getDownloadHandler0(String href, DownloadingPaneContainer downloadingPaneContainer) {
        return new BilibiliSpaceDownloadAction(href,downloadingPaneContainer);
    }

    protected Parser getInfoParser0() {
        return new BilibiliSpaceSearchParser();
    }

    protected SearchPageParser getPageParser0() {
        return new BilibiliSpaceSearchPageParser();
    }
}
