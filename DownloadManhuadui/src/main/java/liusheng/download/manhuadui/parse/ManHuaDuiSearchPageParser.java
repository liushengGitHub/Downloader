package liusheng.download.manhuadui.parse;

import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.Collections;

public class ManHuaDuiSearchPageParser implements SearchPageParser {

    @Override
    public SearchPage parse(Object o) {

        if (o instanceof SearchPage) {
            return (SearchPage) o;
        }
        return new SearchPage(Collections.emptyList(), 1);
    }
}
