package liusheng.download.bilibili.search;

import com.google.gson.Gson;
import liusheng.download.bilibili.entity.SearchJson;
import liusheng.download.bilibili.entity.SearchJson1;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliSearchInfoParser implements Parser<Object,Object> {
    //https://search.bilibili.com/all?keyword=lbw
    public static final String HTTPS_WWW_BILIBILI_COM_VIDEO_AV = "https://search\\.bilibili\\.com/all\\?keyword=";
    private static final Pattern CHECK = Pattern.compile(HTTPS_WWW_BILIBILI_COM_VIDEO_AV + ".*");
    private static final String PREFIX = "window.__INITIAL_STATE__=";

    static Logger logger = Logger.getLogger(BilibiliSearchInfoParser.class);

    @Override
    public Object parse(Object data) throws IOException {
        if (! (data instanceof  String)) throw  new RuntimeException();
        String url = (String) data;
        Connection connection = ConnectionUtils.getConnection(url);

        if (connection == null) {
            return null;
        }

        Document document = connection
                .get();

        Gson gson = new Gson();
        String script = document.select("script").stream().map(Element::html).filter(h -> h.contains(PREFIX)).findFirst().get().substring(PREFIX.length());
        int i = script.lastIndexOf(";(function(){var s");
        if (i == -1) throw new RuntimeException();

        //getMixinFlowList-jump-keyword-webflux
        script = script.substring(0, i);
        Matcher matcher = Pattern.compile("\"getMixinFlowList-.*?\"").matcher(script);

        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            matcher.appendReplacement(sb, "\"getMixinFlow\"");
        }
        matcher.appendTail(sb);

        script = sb.toString();
        Object object;

        if (script.contains("pageinfo")) {
            object = gson.fromJson(script, SearchJson.class);
        } else {
            object = gson.fromJson(script, SearchJson1.class);
        }

        return object;
    }
}
