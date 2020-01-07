package liusheng.downloadCore.entity;

import javafx.scene.Node;
import liusheng.downloadCore.RunnableInfo;
import liusheng.downloadCore.pane.DownloadPane;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractDataBean implements RunnableInfo {
    // 文件夹
    transient File dirFile;
    // 每 P url
    transient String refererUrl;
    // 文件名
    transient String name;
    private transient int quality;
    private transient AtomicLong size;
    private transient AtomicLong allSize;
    private transient AtomicInteger partSize = new AtomicInteger();

    public DownloadPane getDownloadPane() {
        return downloadPane;
    }

    public void setDownloadPane(DownloadPane downloadPane) {
        this.downloadPane = downloadPane;
    }

    private  transient  DownloadPane downloadPane;
    public abstract int getParts();

    public AtomicInteger getPartSize() {
        return partSize;
    }

    private transient Node pane;

    public AtomicLong getAllSize() {
        return allSize;
    }

    public void setAllSize(AtomicLong allSize) {
        this.allSize = allSize;
    }

    public AtomicLong getSize() {
        return size;
    }

    public void setSize(AtomicLong size) {
        this.size = size;
    }

    public Node getPane() {
        return pane;
    }

    public void setPane(Node pane) {
        this.pane = pane;
    }


    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }


    public File getDirFile() {
        return dirFile;
    }

    public void setDirFile(File dirFile) {
        this.dirFile = dirFile;
    }

    public String getRefererUrl() {
        return refererUrl;
    }

    public void setRefererUrl(String refererUrl) {
        this.refererUrl = refererUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  void reset() {
        size.set(0);
        allSize.set(0);
        partSize.set(1);
    }
}
