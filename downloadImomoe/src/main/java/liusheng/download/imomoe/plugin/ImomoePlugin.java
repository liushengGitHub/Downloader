package liusheng.download.imomoe.plugin;

import liusheng.download.imomoe.pane.ImomoeParserDownload;
import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

/**
 * 年: 2019  月: 12 日: 26 小时: 20 分钟: 17
 * 用户名: LiuSheng
 */

public class ImomoePlugin implements SearchPlugin {

    public SearchPluginHolder get() {
        return new SearchPluginHolder(new ImomoeParserDownload(),new SearchLabel("樱花动漫","http://xyhdm.com/search.php?searchword=%s&page=&s"));
    }
}
