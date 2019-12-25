package liusheng.download.manhuadui.bean;

import java.util.List;

public class Cartoon {
    private final String name;
    private final List<Version> versions;

    public String getName() {
        return name;
    }



    public List<Version> getVersions() {
        return versions;
    }

    public Cartoon(String name,  List<Version> versions) {
        this.name = name;
        this.versions = versions;
    }
}
