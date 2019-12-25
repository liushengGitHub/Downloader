package liusheng.download.manhuadui.plugin;

import liusheng.downloadInterface.SearchLabel;
import liusheng.downloadInterface.SearchPlugin;
import liusheng.downloadInterface.SearchPluginHolder;

public class ManhuaduiSearchPlugin implements SearchPlugin {
    @Override
    public SearchPluginHolder get() {
        return  new SearchPluginHolder(new ManhuaduiPaneParser(),new SearchLabel("漫画堆","https://www.manhuadui.com/search/?keywords=%s&page=%s"));
    }
}
