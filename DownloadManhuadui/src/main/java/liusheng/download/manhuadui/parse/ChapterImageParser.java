package liusheng.download.manhuadui.parse;

import liusheng.download.manhuadui.bean.Image;
import liusheng.download.manhuadui.script.MHScriptExecutor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ChapterImageParser extends AbstractParser<List<Image>> {
    @Override
    protected List<Image> trulyParse(Document document) {

        Element element = document.select("div.mainNav")
                // <script> 下的是html 类型 不是text
                .first();

        List<Image> list = new LinkedList<>();
        Optional.ofNullable(element)
                .map(e->e.nextElementSibling())
                .map(e-> e.html())
                .ifPresent(html-> {
                    try {
                        MHScriptExecutor mhScriptExecutor = new MHScriptExecutor();
                        mhScriptExecutor.execute(html);
                        list.addAll( mhScriptExecutor.getChapterImages());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return Collections.unmodifiableList(list);
    }


}
