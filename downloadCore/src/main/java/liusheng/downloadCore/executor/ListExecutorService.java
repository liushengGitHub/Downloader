package liusheng.downloadCore.executor;

import liusheng.downloadCore.config.SystemCodeConfig;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ListExecutorService extends ThreadPoolExecutor {
    private final static Queue<Runnable> taskQueue = new LinkedBlockingQueue<>();


    private static AtomicInteger currentTaskNumber = new AtomicInteger();

    public static AtomicInteger getCurrentTaskNumber() {
        return currentTaskNumber;
    }

    public static Queue<Runnable> getTaskQueue() {
        return taskQueue;
    }


    private static ListExecutorService listExecutorService = new ListExecutorService(
            SystemCodeConfig.mainThreadNumber(),"main");


    private static ListExecutorService listExecutorServiceHelp =
            new ListExecutorService(SystemCodeConfig.helpThreadNumber(),"help");


    public static synchronized ListExecutorService commonExecutorService() {
        return listExecutorService;
    }

    public static synchronized ListExecutorService commonExecutorServicehelp() {
        return listExecutorServiceHelp;
    }


    static {
        // 取出数据
        listExecutorServiceHelp.execute(() -> {
            while (true) {
                try {
                    int taskNumber = SystemCodeConfig.taskNumber();

                    int current = currentTaskNumber.get();

                    if (current < taskNumber) {
                        Runnable runnable = getTaskQueue().poll();

                        if (Objects.nonNull(runnable)) {
                            // 任务加1
                            currentTaskNumber.getAndIncrement();
                            listExecutorServiceHelp.execute(runnable);
                        }
                    }
                } catch (Throwable e) {

                }
            }
        });
    }

    static class DefaultThreadFactory implements ThreadFactory {

        private final AtomicInteger index = new AtomicInteger();
        private final String name;

        public DefaultThreadFactory(String name) {

            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(name + "_" + index.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }

    public ListExecutorService(int fixedSize,String name) {
        this(fixedSize, new DefaultThreadFactory(name));
    }

    public ListExecutorService(int fixedSize, ThreadFactory threadFactory) {
        super(fixedSize, fixedSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10240), threadFactory);
        this.setKeepAliveTime(1, TimeUnit.MILLISECONDS);
        this.allowCoreThreadTimeOut(true);
    }

    public ListExecutorService(Queue<FailTask> queue, ThreadFactory threadFactory) {
        this(Runtime.getRuntime().availableProcessors() * 2, Executors.defaultThreadFactory());
    }

    public ListExecutorService(String name) {
        this(Runtime.getRuntime().availableProcessors() * 2,name);
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
