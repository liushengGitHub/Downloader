package liusheng.download.manhuadui.bean;



import liusheng.downloadCore.entity.AbstractDataBean;
import liusheng.downloadCore.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Image extends AbstractDataBean {
    private final static AtomicInteger size = new AtomicInteger(1);
    private String url;


    public Image() {

    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                '}';
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
        setName( (s.lastIndexOf(".") != -1) ? s : s + ".png");
    }

    @Override
    public int getParts() {
        return 1;
    }
}
