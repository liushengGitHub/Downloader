package liusheng.download.bilibili;

import liusheng.download.bilibili.entity.AdapterParam;
import liusheng.download.bilibili.parser.NewParser;
import liusheng.download.bilibili.parser.OldParser;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DefaultParserAdapter implements ParserAdapter {
    private static final String TAG = "\"dash\"";
    private static final String PREFIX = "window.__playinfo__=";
    public static final String HTTPS_API_BILIBILI_COM_X_PLAYER_PLAYURL_AVID =
            "https://api.bilibili.com/x/player/playurl?avid=%s&cid=%s&qn=80&type=&otype=json&fnver=0&fnval=16";

    @Override
    public Parser<String,?> handle(AdapterParam adapterParam) throws IOException {
        Document document = ConnectionUtils.getConnection(adapterParam.getUrl())
                .execute().
                        parse();
        String script = document.select("script").stream().map(Element::html)
                .filter(s -> s.contains(PREFIX)).findFirst().orElseGet(() -> {
                    // 获取Json格式的
                    Object cid = adapterParam.getMap().get("cid");
                    Object aid = adapterParam.getMap().get("aid");

                    String url = String.format(HTTPS_API_BILIBILI_COM_X_PLAYER_PLAYURL_AVID, aid, cid);
                    try {
                        return ConnectionUtils.getConnection(url).execute().body();
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }

                });
        // 可能是JsoN方式获取的,所有,不能直接直接截取
        if (script.contains(PREFIX)) {
            script = script.substring(PREFIX.length());
        }
        Parser<String,?> parser = null;
        if (script.contains(TAG)) {
            NewParser newParser = new NewParser();
            newParser.setContent(script);
            parser = newParser;
        } else {
            OldParser oldParser = new OldParser();
            oldParser.setContent(script);
            parser = oldParser;
        }
        return parser;
    }
}
