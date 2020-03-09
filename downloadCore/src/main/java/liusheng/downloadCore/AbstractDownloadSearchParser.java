package liusheng.downloadCore;

import liusheng.downloadCore.pane.*;
import liusheng.downloadInterface.SearchItem;

import java.util.Objects;

public abstract class AbstractDownloadSearchParser extends AbstractSearchPaneParser {

    public AbstractDownloadAction getDownloadHandler(String href, DownloadingPaneContainer downloadingPaneContainer) {
        return getDownloadHandler0(href,downloadingPaneContainer);
    }
    protected abstract AbstractDownloadAction getDownloadHandler0(String href, DownloadingPaneContainer downloadingPaneContainer);

    protected AbstractSearchItemPane getSearchItemPane(DownloadingPane pane, Integer i, SearchItem item) {
        SearchDownloadItemPane searchDownloadItemPane = new SearchDownloadItemPane(i, item, pane.getDownloadingPaneContainer());
        searchDownloadItemPane.getDownload().setOnAction( getDownloadHandler(item.getHref(), pane.getDownloadingPaneContainer()));
        return searchDownloadItemPane;
    }
}
