package liusheng.downloadCore.executor;

import liusheng.downloadCore.config.SystemConfig;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FailListExecutorService extends ThreadPoolExecutor {
    private final static Queue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private static AtomicInteger currentTaskNumber = new AtomicInteger();

    public static AtomicInteger getCurrentTaskNumber() {
        return currentTaskNumber;
    }

    public static Queue<Runnable> getTaskQueue() {
        return taskQueue;
    }


    private static int coreSize = Runtime.getRuntime().availableProcessors();
    private static FailListExecutorService failListExecutorService = new FailListExecutorService(
            coreSize * 3);
    private static FailListExecutorService failListExecutorServiceHelp = new FailListExecutorService(coreSize * 2);


    private final Logger logger = Logger.getLogger(FailListExecutorService.class);

    public static synchronized FailListExecutorService commonExecutorService() {
        return failListExecutorService;
    }

    public static synchronized FailListExecutorService commonExecutorServicehelp() {
        return failListExecutorServiceHelp;
    }


    static {
        // 取出数据
        failListExecutorServiceHelp.execute(() -> {
            while (true) {
                try {
                    int taskNumber = SystemConfig.getTaskNumber();
                    int current = currentTaskNumber.get();

                    if (current < taskNumber) {
                        Runnable runnable = getTaskQueue().poll();

                        if (Objects.nonNull(runnable)) {
                            currentTaskNumber.getAndIncrement();
                            failListExecutorServiceHelp.execute(runnable);
                        }
                    }
                } catch (Throwable e) {

                }
            }
        });
    }


    public FailListExecutorService(int fixedSize) {
        this(fixedSize, Executors.defaultThreadFactory());
    }

    public FailListExecutorService(int fixedSize, ThreadFactory threadFactory) {
        super(fixedSize, fixedSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1024), threadFactory);
        this.setKeepAliveTime(1, TimeUnit.MILLISECONDS);
        this.allowCoreThreadTimeOut(true);
    }

    public FailListExecutorService(Queue<FailTask> queue, ThreadFactory threadFactory) {
        this(Runtime.getRuntime().availableProcessors() * 2, Executors.defaultThreadFactory());
    }

    public FailListExecutorService() {
        this(Runtime.getRuntime().availableProcessors() * 2);
    }

    public FailListExecutorService(Queue<FailTask> queue) {
        this(Runtime.getRuntime().availableProcessors() * 2);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {

      /*  // 加入失败的任务
        if (!Objects.isNull(t) && r instanceof FailTask) {

            logger.info(t);

            queue.add((FailTask) r);

        }*/

    }
}
