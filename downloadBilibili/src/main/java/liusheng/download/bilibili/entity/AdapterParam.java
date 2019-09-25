package liusheng.download.bilibili.entity;

import java.util.HashMap;
import java.util.Map;

public class AdapterParam {
    private String url;
    private Map<String,Object> map = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}