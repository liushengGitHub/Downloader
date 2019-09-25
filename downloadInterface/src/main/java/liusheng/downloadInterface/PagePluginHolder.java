package liusheng.downloadInterface;

import javafx.scene.layout.Pane;

public class PagePluginHolder {
    private final Parser<Object, Pane> parser;
    private final SearchLabel show;

    public PagePluginHolder(Parser<Object, Pane> parser, SearchLabel show) {
        this.parser = parser;
        this.show = show;
    }

    public Parser<Object, Pane> getParser() {
        return parser;
    }

    public SearchLabel getShow() {
        return show;
    }
}
