package liusheng.downloadInterface;

import javafx.scene.control.Label;

public class SearchLabel extends Label {
    private final String pattern ;

    public SearchLabel(String text, String pattern) {
        super(text);
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
