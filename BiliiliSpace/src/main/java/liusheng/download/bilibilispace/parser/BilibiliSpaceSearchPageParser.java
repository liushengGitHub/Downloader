package liusheng.download.bilibilispace.parser;

import liusheng.download.bilibilispace.entity.SpaceEntity;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 年: 2019  月: 12 日: 31 小时: 14 分钟: 20
 * 用户名: LiuSheng
 */

public class BilibiliSpaceSearchPageParser implements SearchPageParser {

    public static final String HTTPS_WWW_BILIBILI_COM_VIDEO_AV_S = "https://www.bilibili.com/video/av%s";

    public SearchPage parse(Object o) {
        SpaceEntity spaceEntity = (SpaceEntity) o;
        SpaceEntity.DataBean data = spaceEntity.getData();
        Integer page = Optional.ofNullable(data)
                .map(SpaceEntity.DataBean::getPage)
                .map(SpaceEntity.DataBean.PageBean::getCount).map(a -> a % 30 == 0 ? a / 30 : a / 30 + 1).orElse(2);

        List<SearchItem> searchItems = Optional.ofNullable(data).map(SpaceEntity.DataBean::getList)
                .map(SpaceEntity.DataBean.ListBean::getVlist).map(list -> {
                    return list.stream().map(vlistBean -> {
                        SearchItem searchItem = new SearchItem();

                        int aid = vlistBean.getAid();
                        searchItem.setHref(String.format(HTTPS_WWW_BILIBILI_COM_VIDEO_AV_S,aid));

                        String author = vlistBean.getAuthor();

                        searchItem.setAuthor(author);

                        String pic = vlistBean.getPic();

                        searchItem.setImgSrc(pic);

                        String title = vlistBean.getTitle();
                        searchItem.setTitle(StringUtils.fileNameHandle(title));

                        return searchItem;
                    }).collect(Collectors.toList());
                }).orElse(Collections.emptyList());


        return new SearchPage(searchItems,page);
    }
}
