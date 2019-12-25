package liusheng.download.manhuadui.script;

import liusheng.download.manhuadui.bean.Image;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MHScriptExecutor implements JSScriptExecutor {
    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private final List<Image> imageLinkedList = new LinkedList<>();
    private static final String PREFIX = "https://mhcdn.manhuazj.com/";

    // https://res.333dm.com
    //https://res02.333dm.com
    @Override
    public void execute(String jsScript) throws Exception {
        ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");
        InputStream resourceAsStream1 = MHScriptExecutor.class.getClassLoader().getResourceAsStream("manhuadui/crypto.js");
        InputStream resourceAsStream2 = MHScriptExecutor.class.getClassLoader().getResourceAsStream("manhuadui/manhuadui.js");
        engine.eval(new InputStreamReader(resourceAsStream1));
        engine.eval(new InputStreamReader(resourceAsStream2));
        engine.eval(jsScript);

        Invocable invocable = (Invocable) engine;

        String imagesStr = (String) invocable.invokeFunction("decrypt20180904");

        List<String> images = new LinkedList<>();
        String regex = "\".*?\"";
        Matcher matcher = Pattern.compile(regex).matcher(imagesStr);
        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length() - 1).replace("\\", "");
            images.add(group);
        }
        // 获取真实的地址
        String[] array = images.toArray(new String[0]);
        IntStream.rangeClosed(1, images.size()).forEach(i -> {
            try {
                String imgUrl = (String) invocable.invokeFunction("getChapterImage", array, i);
                imageLinkedList.add(new Image(imgUrl));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public List<Image> getChapterImages() {
        return imageLinkedList;
    }
}
