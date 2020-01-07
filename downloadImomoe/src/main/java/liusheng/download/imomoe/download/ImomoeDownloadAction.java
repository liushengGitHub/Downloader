package liusheng.download.imomoe.download;

import javafx.event.ActionEvent;
import liusheng.downloadCore.AbstractDownloadAction;
import liusheng.downloadCore.pane.DownloadingPaneContainer;
import liusheng.downloadCore.util.ConnectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 年: 2019  月: 12 日: 26 小时: 21 分钟: 13
 * 用户名: LiuSheng
 */

public class ImomoeDownloadAction extends AbstractDownloadAction {
    public static final String HTTPS_ZY_BAJIEZIYUAN_COM_M_3_U_8_PHP_URL = "https://zy.bajieziyuan.com/m3u8.php?url=";

    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    public ImomoeDownloadAction(String href, DownloadingPaneContainer downloadingPaneContainer) {
        super(href,downloadingPaneContainer);
    }


    @Override
    protected void asynHandle(ActionEvent event) {
        try {
            Document document = ConnectionUtils.getConnection(href).get();

            document.select("body > div.container > div:nth-child(1) > div > div > div:nth-child(2) > div.myui-content__detail > div.operate.clearfix > a.btn.btn-warm")
                    .stream().findFirst().ifPresent(element -> {
                String url = element.absUrl("href");
                processPlayUrl(url,href);
            });

        } catch (Throwable throwable) {

            throw  new RuntimeException(throwable);
        }
    }

    private void processPlayUrl(String url, String refer) {
        try{
            Document document = ConnectionUtils.getConnection(url)
                    .referrer(refer)
                    .get();

            document.select("script")
                    .stream()
                    .map(Element::html)
                    .filter(h->h.contains("now"))
                    .findFirst().ifPresent(script->{
                ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");

                try {
                    engine.eval(script);

                    String now = (String) engine.get("now");
                    String pn = (String) engine.get("pn");

                    if (!pn.equals("kuyun")) {
                        now = HTTPS_ZY_BAJIEZIYUAN_COM_M_3_U_8_PHP_URL + now;
                    }

                    System.out.println(now);
                    System.out.println(pn);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        }catch (Throwable throwable) {
            throw  new RuntimeException(throwable);
        }
    }
}
