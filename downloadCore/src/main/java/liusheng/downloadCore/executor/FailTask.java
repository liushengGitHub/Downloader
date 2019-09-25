package liusheng.downloadCore.executor;

import org.apache.log4j.Logger;

/**
 * 代理模式
 */
public class FailTask implements Runnable {
    private final Logger logger = Logger.getLogger(FailTask.class);
    public static final int COUNT = 3;


    private final Runnable task;
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void count() {
        count++;
    }

    public FailTask(Runnable task) {
        this.task = task;
    }


    @Override
    public void run() {
        task.run();
    }
}
