package liusheng.download.manhuadui.bean;

import java.util.List;

public class Version {
    private final String version;
    private final List<Chapter> chapters;

    public String getVersion() {
        return version;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public Version(String version, List<Chapter> chapters) {
        this.version = version;
        this.chapters = chapters;
    }
}
