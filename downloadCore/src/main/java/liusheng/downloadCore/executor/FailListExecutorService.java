package liusheng.downloadCore.executor;

import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;

public class FailListExecutorService extends ThreadPoolExecutor {
    private static Queue<FailTask> failTaskQueue = new LinkedBlockingQueue<>();
    private static int coreSize = Runtime.getRuntime().availableProcessors();
    private static FailListExecutorService failListExecutorService = new FailListExecutorService(
            coreSize * 3, failTaskQueue);
    ;
    private static FailListExecutorService failListExecutorServiceHelp = new FailListExecutorService(coreSize * 2, failTaskQueue);
    ;
    private final Logger logger = Logger.getLogger(FailListExecutorService.class);
    public final Queue<FailTask> queue;

    public static synchronized FailListExecutorService commonExecutorService() {
        return failListExecutorService;
    }

    public static synchronized FailListExecutorService commonExecutorServicehelp() {
        return failListExecutorServiceHelp;
    }

    public Queue<FailTask> failTaskQueue() {
        return queue;
    }

    public FailListExecutorService(int fixedSize, Queue<FailTask> queue) {
        this(fixedSize, queue, Executors.defaultThreadFactory());
    }

    public FailListExecutorService(int fixedSize, Queue<FailTask> queue, ThreadFactory threadFactory) {
        super(fixedSize, fixedSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1024), threadFactory);
        this.queue = queue;
        this.setKeepAliveTime(1, TimeUnit.MILLISECONDS);
        this.allowCoreThreadTimeOut(true);
    }

    public FailListExecutorService(Queue<FailTask> queue, ThreadFactory threadFactory) {
        this(Runtime.getRuntime().availableProcessors() * 2, queue, Executors.defaultThreadFactory());
    }

    public FailListExecutorService() {
        this(Runtime.getRuntime().availableProcessors() * 2, new ConcurrentLinkedQueue());
    }

    public FailListExecutorService(Queue<FailTask> queue) {
        this(Runtime.getRuntime().availableProcessors() * 2, queue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {

        // 加入失败的任务
        if (!Objects.isNull(t) && r instanceof FailTask) {

            logger.info(t);

            queue.add((FailTask) r);

        }

    }
}
