package liusheng.downloadInterface;

import java.util.List;

public class SearchPage {
    private List<SearchItem> items;
    private int pages;

    public SearchPage(List<SearchItem> items, int pages) {
        this.items = items;
        this.pages = pages;
    }

    public SearchPage() {

    }


    public void setItems(List<SearchItem> items) {
        this.items = items;
    }

    public List<SearchItem> getItems() {
        return items;
    }
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
