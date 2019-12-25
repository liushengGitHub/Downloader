package liusheng.download.manhuadui.parse;

import liusheng.download.manhuadui.bean.Cartoon;
import liusheng.download.manhuadui.bean.Chapter;
import liusheng.download.manhuadui.bean.Version;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


public class CartoonParser extends AbstractParser<Cartoon> {
    //https://www.manhuadui.com/manhua/jinjidejuren/
    private static final Pattern CHECK_REGEX = Pattern.compile("https://www\\.manhuadui\\.com/manhua/\\S+");

    @Override
    protected Cartoon trulyParse(Document document) {
        String title = document.select("title").text();

        List<Version> versions = new LinkedList<>();

        document.select("div.zj_list.autoHeight")
                .forEach(div -> {
                    Elements elements = div.select(".zj_list_head");
                    Element h2 = elements.select("h2").first();

                    StringBuilder sb = new StringBuilder();
                    Element em = elements.select("em").first();
                    if (h2 == null || em == null) return;

                    sb.append(h2.text().trim());
                    sb.append(em.text().trim());

                    List<Chapter> chapters = new LinkedList<>();

                    div.select("div.zj_list_con.autoHeight li a")
                            .forEach(a -> {
                                String title1 = a.attr("title");
                                String chapterUrl = a.attr("abs:href");

                                chapters.add(new Chapter(chapterUrl, title1));

                            });
                    versions.add(new Version(sb.toString(), chapters));
                });

        return new Cartoon(title, versions);
    }

}
