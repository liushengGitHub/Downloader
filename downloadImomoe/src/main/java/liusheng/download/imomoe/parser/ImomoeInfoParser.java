package liusheng.download.imomoe.parser;

import liusheng.download.imomoe.entity.ImomoneEntity;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;
import liusheng.downloadInterface.SearchItem;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 年: 2019  月: 12 日: 26 小时: 20 分钟: 51
 * 用户名: LiuSheng
 */

public class ImomoeInfoParser implements Parser<String, ImomoneEntity> {
    @Override
    public ImomoneEntity parse(String url) throws IOException {

        ImomoneEntity imomoneEntity = new ImomoneEntity();
        Document document = ConnectionUtils.getConnection(url).get();

        List<SearchItem> searchItems = document.select("body > div.wrap > div > div > div.col-md-wide-7.col-xs-1 > div > div > div.myui-panel_bd.col-pd.clearfix > ul li")
                .stream().map(element -> {
                    SearchItem searchItem = new SearchItem();

                    element.select(".thumb a")
                            .stream().findFirst()
                            .ifPresent(thumb -> {
                                String title = thumb.attr("title");
                                searchItem.setTitle(title);
                                searchItem.setHref(thumb.absUrl("href"));
                                searchItem.setImgSrc(thumb.absUrl("data-original"));

                            });
                    element.select("div.detail > p:nth-child(3)")
                            .stream().findFirst()
                            .ifPresent(detail->{
                                searchItem.setAuthor(detail.text());
                            });

                    return searchItem;
                }).collect(Collectors.toList());


        imomoneEntity.setSearchItems(searchItems);
        return imomoneEntity;
    }
}
