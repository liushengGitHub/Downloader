package liusheng.download.manhuadui.bean;



import liusheng.downloadCore.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Image {
    private final static AtomicInteger size = new AtomicInteger(1);
    private String url;

    private String fileName;

    public Image() {

    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image(String url) {
        this.url = url;
        String s = StringUtils.urlToFileName(url);
        s = (s.lastIndexOf(".") != -1) ? s : s + ".png";
        this.fileName = size.getAndIncrement() + "_" + s;
    }
}
