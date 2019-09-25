package liusheng.download.bilibili.parser;

import com.google.gson.Gson;
import liusheng.download.bilibili.entity.PagesBean;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class PageInfoParser implements Parser<String, PagesBean> {
    //                                                           http://www.bilibili.com/video/av67748053
    public static final String HTTPS_WWW_BILIBILI_COM_VIDEO_AV = "https://www.bilibili.com/video/av%s";
    private static final String PREFIX = "window.__INITIAL_STATE__=";
    private final Gson gson = new Gson();

    static Logger logger = Logger.getLogger(PageInfoParser.class);


    @Override
    public PagesBean parse(String url) throws IOException {
        Connection connection = ConnectionUtils.getConnection(url);

        if (connection == null) {
            return null;
        }

        Document document = connection
                .get();

        String script = document.select("script").stream().map(Element::html).filter(h -> h.contains(PREFIX)).findFirst().get().substring(PREFIX.length());
        int i = script.lastIndexOf("]};(function(){");
        if (i == -1) throw new RuntimeException();
        PagesBean pageBean = gson.fromJson(script.substring(0, i + 2), PagesBean.class);
        return pageBean;
    }
}
