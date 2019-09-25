package liusheng.download.bilibili.search;

import liusheng.download.bilibili.parser.BilibiliParser;
import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

public class BilibiliSearchPlugin implements SearchPlugin {

    @Override
    public SearchPluginHolder get() {
        return new SearchPluginHolder(
                new BilibiliParser(),new SearchLabel("Bilibili",
                "https://search.bilibili.com/all?keyword=%s&page=%s"));
    }
}
