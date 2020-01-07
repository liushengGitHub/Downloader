package liusheng.downloadCore.util;

public class StringUtils {
    public static String fileNameHandle(String name) {
        name = name.replaceAll("[\\\\:*<>?|/\\s]", "");
        return name;
    }

    public static boolean isEmpty(String name) {
        return name == null || name.length() == 0;
    }

    public static String htmlAbsolutionPath(String url) {
        if (url.startsWith("http") || url.startsWith("https")) return url;

        if (url.startsWith("//")) return "https:" + url;
        if (url.startsWith("://")) return "https" + url;

        return null;
    }

    public static String delelteHtmlTag(String content) {


        return content.replaceAll("(<.+?>)|(</.+?>)", "");
    }

    public static String urlToFileName(String url) {
        int start = url.lastIndexOf("/");
        if (start == -1) throw new IllegalArgumentException();
        String temp = url.substring(start + 1);
        return temp;
    }

    public static String urlProcess(String url) {
        if (url.startsWith("http")){
            return  url;
        }
        if (url.startsWith("//")) {
            url = "http:" + url;
            return  url ;
        }
        if (!url.startsWith("/")) {
            return  null;
        }
        return url;
    }
}
