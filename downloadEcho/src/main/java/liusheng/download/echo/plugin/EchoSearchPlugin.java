package liusheng.download.echo.plugin;

import liusheng.download.echo.parser.EchoParserDownload;
import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPluginHolder;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 00
 * 用户名: LiuSheng
 */

public class EchoSearchPlugin implements liusheng.downloadInterface.SearchPlugin {
    public SearchPluginHolder get() {
        return new SearchPluginHolder( new EchoParserDownload()

                ,new SearchLabel("Echo","http://www.app-echo.com/api/search/sound?keyword=%s&page=%s&limit=10"));
    }
}
