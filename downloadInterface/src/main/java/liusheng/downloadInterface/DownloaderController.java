package liusheng.downloadInterface;

public interface DownloaderController {
    int INIT = -1;
    int EXECUTE = 0;
    int PAUSE = 1;
    int CANCEL = 2;
    int FINISHED = 3;
    int EXCEPTION = 4;

    void pause();

    void cancel();

    int getState();

    void setState(int state);
}
