package liusheng.download.bilibili.search;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import liusheng.download.bilibili.entity.ResultBean;
import liusheng.download.bilibili.entity.SearchJson;
import liusheng.download.bilibili.entity.SearchJson1;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.SearchItem;
import liusheng.downloadInterface.SearchPage;
import liusheng.downloadInterface.SearchPageParser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BilibiliSearchPageParser implements SearchPageParser {
    private final Gson gson = new Gson();

    @Override
    public SearchPage parse(Object parse) {

        try {

            List<SearchItem> searchItems = getSearchItems(parse);

            Integer pageNumber = Optional.ofNullable(parse)
                    .map(o -> {
                        if (o instanceof SearchJson) {
                            SearchJson json = (SearchJson) o;
                            return Optional.of(json)
                                    .map(SearchJson::getFlow)
                                    .map(SearchJson.FlowBean::getGetMixinFlow)
                                    .map(SearchJson.FlowBean.GetMixinFlow::getExtra)
                                    .map(SearchJson.FlowBean.GetMixinFlow.ExtraBean::getNumPages)
                                    .orElse(1);
                        } else if (o instanceof SearchJson1) {
                            SearchJson1 searchJson1 = (SearchJson1) o;

                            return Optional.of(searchJson1)
                                    .map(SearchJson1::getFlow)
                                    .map(SearchJson1.FlowBean::getGetMixinFlow)
                                    .map(SearchJson1.FlowBean.GetMixinFlow::getExtra)
                                    .map(SearchJson1.FlowBean.GetMixinFlow.ExtraBean::getNumPages).orElse(1);
                        } else {
                            return 1;
                        }
                    }).orElse(1);
            return new SearchPage(searchItems, pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<SearchItem> getSearchItems(Object parse) {
        List<ResultBean> resultBeans = (List<ResultBean>) Optional.ofNullable(parse)
                .map(o -> {
                    if (o instanceof SearchJson) {
                        SearchJson json = (SearchJson) o;
                        return Optional.of(json)
                                .map(SearchJson::getFlow)
                                .map(SearchJson.FlowBean::getGetMixinFlow)
                                .map(SearchJson.FlowBean.GetMixinFlow::getResult)
                                .map(list -> {
                                    return list.stream().filter(r -> "video".equalsIgnoreCase(r.getResult_type())).findFirst().orElse(null);
                                }).map(SearchJson.FlowBean.GetMixinFlow.ResultBean::getData).map(
                                        // 通过TypeToken 或者数据间接转换
                                        m -> gson.fromJson(gson.toJson(m), new TypeToken<List<ResultBean>>() {
                                        }.getType())

                                ).orElse(Collections.emptyList());
                    } else if (o instanceof SearchJson1) {
                        SearchJson1 searchJson1 = (SearchJson1) o;

                        return Optional.of(searchJson1)
                                .map(SearchJson1::getFlow)
                                .map(SearchJson1.FlowBean::getGetMixinFlow)
                                .map(SearchJson1.FlowBean.GetMixinFlow::getResult).orElse(Collections.<ResultBean>emptyList());
                    } else {
                        return Collections.<ResultBean>emptyList();
                    }
                }).orElse(Collections.<ResultBean>emptyList());
        List<SearchItem> items = resultBeans.stream().map(r -> {
            return new SearchItem(r.getArcurl(), StringUtils.delelteHtmlTag(r.getTitle()), StringUtils.htmlAbsolutionPath(r.getPic()), r.getAuthor());
        }).collect(Collectors.toList());

        return items;
    }
}
