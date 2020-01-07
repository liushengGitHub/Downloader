package liusheng.downloadCore;

import javafx.scene.Node;

/**
 * 年: 2019  月: 12 日: 26 小时: 08 分钟: 40
 * 用户名: LiuSheng
 */

public class DefaultRunnableInfo implements RunnableInfo {
    private final Node pane;
    private final String refererUrl;

    public DefaultRunnableInfo(Node pane, String refererUrl) {
        this.pane = pane;
        this.refererUrl = refererUrl;
    }

    @Override
    public String getRefererUrl() {
        return refererUrl;
    }

    @Override
    public Node getPane() {
        return pane;
    }
}
