package liusheng.downloadCore.pane;

import liusheng.downloadInterface.DownloaderController;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class DefaultDownloaderController implements DownloaderController {
    private int state = DownloaderController.INIT;

    @Override
    public synchronized void pause() {
        if (state == DownloaderController.EXECUTE) {
            state = DownloaderController.PAUSE;
        } else if (state == DownloaderController.PAUSE) {
            state = DownloaderController.EXECUTE;
        }
    }

    @Override
    public synchronized void cancel() {
        setState(DownloaderController.CANCEL);
    }

    @Override
    public synchronized int getState() {
        return state;
    }

    @Override
    public synchronized void setState(int state) {
        if (this.state == DownloaderController.CANCEL || this.state == DownloaderController.FINISHED) {
            return;
        }
        this.state = state;
    }
}
