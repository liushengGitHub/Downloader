package liusheng.download.bilibili.search;

import liusheng.download.bilibili.parser.BilibiliParser;
import liusheng.download.bilibili.parser.BilibiliVideoSubPageParser;
import liusheng.downloadInterface.PagePlugin;
import liusheng.downloadInterface.PagePluginHolder;
import liusheng.downloadInterface.SearchLabel;

public class BililiPagePlugin implements PagePlugin {
    @Override
    public PagePluginHolder get() {
        return new PagePluginHolder(
                new BilibiliVideoSubPageParser(),new SearchLabel("Bilibili",
                "https://www.bilibili.com/video/av%s")
        );
    }
}
