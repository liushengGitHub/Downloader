package liusheng.downloadCore.pane;

import liusheng.downloadInterface.DownloaderController;

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
        if (state != DownloaderController.CANCEL) {
            state = DownloaderController.CANCEL;
        }
    }

    @Override
    public synchronized int getState() {
        return state;
    }

    @Override
    public synchronized void setState(int state) {
         this.state = state;
    }
}
