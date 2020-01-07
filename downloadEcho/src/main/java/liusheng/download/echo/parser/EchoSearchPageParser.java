package liusheng.download.echo.parser;

import liusheng.download.echo.entity.EchoEntity;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 05
 * 用户名: LiuSheng
 */

public class EchoSearchPageParser implements SearchPageParser {
    public SearchPage parse(Object o) {
        if (Objects.isNull(o) || !(o instanceof EchoEntity)) throw  new IllegalArgumentException();
        EchoEntity echoEntity = (EchoEntity) o;


        SearchPage searchPage = new SearchPage();

        process(echoEntity,searchPage);

        searchPage.setPages(2);
        return searchPage;
    }

    private void process(EchoEntity echoEntity, SearchPage searchPage) {
        Optional.ofNullable(echoEntity.getData())
                .map(dataBeans -> {
                    try{
                        return dataBeans.stream()
                                .map(dataBean -> {
                                    return  new SearchItem(dataBean.getSource(),
                                            dataBean.getName(),dataBean.getPic(),
                                            dataBean.getInfo());
                                }).collect(Collectors.toList());
                    }catch (Throwable throwable) {
                        return Collections.<SearchItem>emptyList();
                    }
                }).ifPresent(searchPage::setItems);
    }
}
