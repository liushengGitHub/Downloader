package liusheng.downloadCore.search;

import liusheng.downloadCore.pane.DownloadingPane;

public class SearchParam {
    private final String pattern;
    private final String keyWord;
    private DownloadingPane downloadingPane;

    public DownloadingPane getDownloadingPane() {
        return downloadingPane;
    }

    public SearchParam(String pattern, String keyWord, DownloadingPane downloadingPane) {
        this.pattern = pattern;
        this.keyWord = keyWord;
        this.downloadingPane = downloadingPane;
    }

    public String getPattern() {
        return pattern;
    }

    public String getKeyWord() {
        return keyWord;
    }
}
