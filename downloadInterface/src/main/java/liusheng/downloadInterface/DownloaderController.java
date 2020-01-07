package liusheng.downloadInterface;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public interface DownloaderController {
    int INIT = -1;
    int EXECUTE = 0;
    int PAUSE = 1;
    int CANCEL = 2;
    int FINISHED = 3;
    int EXCEPTION = 4;
    Queue<Runnable> stateQueue = new LinkedList<>();
    ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
            (ThreadFactory) (run) -> {
                Thread thread = new Thread();
                thread.setName("State Thread");
                return thread;
            });

    void pause();

    void cancel();

    int getState();

    void setState(int state);
}
