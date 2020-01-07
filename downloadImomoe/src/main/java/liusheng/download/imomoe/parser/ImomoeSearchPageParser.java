package liusheng.download.imomoe.parser;

import liusheng.download.imomoe.entity.ImomoneEntity;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.Objects;

/**
 * 年: 2019  月: 12 日: 26 小时: 21 分钟: 07
 * 用户名: LiuSheng
 */

public class ImomoeSearchPageParser implements SearchPageParser {
    @Override
    public SearchPage parse(Object o) {
        if (Objects.isNull(o) || !(o instanceof ImomoneEntity)) throw new RuntimeException();
        ImomoneEntity imomoneEntity = (ImomoneEntity) o;
        return new SearchPage(imomoneEntity.getSearchItems(), 2);
    }
}
