package liusheng.downloadInterface;

public class SearchItem {
    private String href;
    private String title;
    private String imgSrc;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public String getHref() {
        return href;
    }
    public String getImgSrc() {
        return imgSrc;
    }



    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public SearchItem() {
    }


    public void setHref(String href) {
        this.href = href;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public SearchItem(String href, String title, String imgSrc, String author) {

        this.href = href;
        this.title = title;
        this.imgSrc = imgSrc /*+ "@100w_100h.webp"*/;
        this.author = author;
    }

}
