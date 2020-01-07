package liusheng.download.bilibilispace.plugins;

import liusheng.download.bilibilispace.parser.BilibiliSpaceDownloadSearchPaneParser;
import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

/**
 * 年: 2019  月: 12 日: 31 小时: 14 分钟: 12
 * 用户名: LiuSheng
 */

public class BilibiliSpaceSearchPlugin implements SearchPlugin {
    public SearchPluginHolder get() {
        return new SearchPluginHolder(new BilibiliSpaceDownloadSearchPaneParser(),
                new SearchLabel("BiliSpace","https://api.bilibili.com/x/space/arc/search?mid=%s&ps=30&tid=0&pn=%s&keyword=&order=pubdate&jsonp=jsonp"));
    }
}
