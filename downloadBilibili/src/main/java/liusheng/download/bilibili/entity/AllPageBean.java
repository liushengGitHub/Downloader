package liusheng.download.bilibili.entity;

public class AllPageBean {

    private String url ;
    private int page;
    private String part;
    private int duration;

    public AllPageBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AllPageBean(String url, int page, String part, int duration) {
        this.url = url;
        this.page = page;
        this.part = part;
        this.duration = duration;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
