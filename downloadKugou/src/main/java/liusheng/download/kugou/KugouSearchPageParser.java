package liusheng.download.kugou;

import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.List;
import java.util.stream.Collectors;

public class KugouSearchPageParser implements SearchPageParser {
    public SearchPage parse(Object o) {
        if (o instanceof KugouSearchEntity) {
            KugouSearchEntity searchEntity = (KugouSearchEntity) o;

            List<SearchItem> list = searchEntity.getData().getLists().stream()
                    .map(listsBean -> {
                        String hash = listsBean.getFileHash();
                        String albumID = listsBean.getAlbumID();
                        String fileName = listsBean.getSongName();
                        String singerName = listsBean.getSingerName();

                        return new SearchItem(String.format("https://wwwapi.kugou.com/yy/index.php?r=play/getdata&hash=%s&album_id=%s&mid=d512344d5cc5df4e7c85148b1faca9e3"
                                , hash, albumID)
                                , StringUtils.delelteHtmlTag(fileName), null, singerName);
                    }).collect(Collectors.toList());
            return new SearchPage(list, 2);
        }
        throw new IllegalArgumentException();
    }
}
