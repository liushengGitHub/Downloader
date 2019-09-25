package liusheng.download.bilibili.parser;

import com.google.gson.Gson;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public class AbstractParser<T> implements Parser<String, T> {
    private final Gson gson = new Gson();
    private static final String PREFIX = "window.__playinfo__=";
    private String content;

    private final Logger logger;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private final Class<T> clazz;

    {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];

        logger = Logger.getLogger(clazz);
    }

    @Override
    public T parse(String url) throws IOException {
        if (content == null) {

            Document document = ConnectionUtils.getConnection(url).get();
            String script = document.select("script").stream().map(Element::html).filter(s -> s.contains(PREFIX)).findFirst().get();
            content = script.substring(PREFIX.length());

        }
        String script = content;

        logger.info(script);

        return gson.fromJson(script, clazz);
    }
}
