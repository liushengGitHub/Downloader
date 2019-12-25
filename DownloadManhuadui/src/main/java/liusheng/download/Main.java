package liusheng.download;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        String chapterImages = "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";
       // chapterImages = "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";

        ScriptEngineManager manager
                = new ScriptEngineManager();

        ScriptEngine js = manager.getEngineByExtension("js");
        ClassLoader classLoader = Main.class.getClassLoader();
        js.eval(new InputStreamReader(classLoader.getResourceAsStream("manhuadui/crypto.js")));
        js.eval(new InputStreamReader(classLoader.getResourceAsStream("manhuadui/manhuadui.js")));

        Invocable invocable = (Invocable) js;

        chapterImages = (String) invocable.invokeFunction("decrypt20180904", chapterImages);

        String regex = "\".*?\"";
        Matcher matcher = Pattern.compile(regex).matcher(chapterImages);
        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length() - 1).replace("\\","");
            System.out.println(group);
        }
        System.out.println(chapterImages);
    }


}
