package liusheng.download.kugou;

import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

public class KugouSearchPlugin implements SearchPlugin {
    public SearchPluginHolder get() {                                                             //https://songsearch.kugou.com/song_search_v2?keyword=%E4%B8%96%E6%9C%AB%E6%AD%8C%E8%80%85&page=1&tag=em
        return new SearchPluginHolder(new KugouSearchParser(), new SearchLabel("酷狗", "https://songsearch.kugou.com/song_search_v2?keyword=%s&page=%s&&platform=WebFilter"));
    }
}
