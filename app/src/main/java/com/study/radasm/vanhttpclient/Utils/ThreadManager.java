package com.study.radasm.vanhttpclient.Utils;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程管理者
 * <p/>
 * Created by RadAsm on 15/6/3.
 */
public class ThreadManager {


    private static ThreadPoolExecutor longExecutor;
    private static ThreadPoolExecutor shortExecutor;
    private static Object longLock =new Object();
    private static Object shortLock =new Object();
    /**
     * 执行具备长池中的任务
     * @param runnable
     */
    public static void executeLong(Runnable runnable) {
        synchronized (longLock) {
            if (longExecutor == null) {
                ThreadPoolProxy proxy = new ThreadPoolProxy(5, 5, 100);
                longExecutor = proxy.createPool();
            }
        }
        longExecutor.execute(runnable);
    }
    /**
     * 取消长池中的对象
     * @param runnable
     */
    public static void cancelLong(Runnable runnable){
        if(longExecutor==null||longExecutor.isShutdown()||longExecutor.isTerminated()){
            return ;
        }else{
            longExecutor.getQueue().remove(runnable);
        }
    }

    /**
     * 执行具备短池中的任务
     * @param runnable
     */
    public static void executeShort(Runnable runnable) {
        synchronized (shortLock) {
            if (shortExecutor == null) {
                ThreadPoolProxy proxy = new ThreadPoolProxy(2, 2, 5);
                shortExecutor = proxy.createPool();
            }
        }
        shortExecutor.execute(runnable);
    }

    /**
     * 取消短池中的对象
     * @param runnable
     */
    public static void cancelShort(Runnable runnable){
        if(shortExecutor==null||shortExecutor.isShutdown()||shortExecutor.isTerminated()){
            return ;
        }else{
            shortExecutor.getQueue().remove(runnable);
        }
    }




    /**
     * =======================线程池代理类======================
     */
    private static class ThreadPoolProxy {


        /**
         * 构造连续执行的线程池
         *
         * @return
         */
        public static ThreadPool createSerialPool() {
            ThreadPool threadPool = null;

            threadPool = new ThreadPool(10);
            return threadPool;
        }


        private int coreNum;
        private int maxNum;
        private long keepTime;
        private ThreadPoolExecutor pool;

        public ThreadPoolProxy(int coreNum, int maxNum, long keepTime) {
            this.coreNum = coreNum;
            this.maxNum = maxNum;
            this.keepTime = keepTime;
        }

        public ThreadPoolExecutor createPool() {
            if (pool == null) {

                pool = new ThreadPoolExecutor(coreNum, maxNum, keepTime,
                        TimeUnit.MICROSECONDS,
                        new LinkedBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            return pool;
        }
    }

    /**====================线程池代理类========================*/



    /**====================一般性的线程池======================*/


    private static class ThreadPool {
        /**
         * 放置task的list，使用LinkedList方便进行增删
         */
        private LinkedList<Runnable> taskLists;
        /**
         * list中存储的最大的可运行task的数量
         */
        private int maxCount;
        /**
         * 当前队列中的task的个数
         */
        private AtomicInteger count;

        ThreadPool(int maxCount) {
            this.maxCount = maxCount;
            this.taskLists = new LinkedList<Runnable>();
            this.count = new AtomicInteger(0);
        }

        public synchronized void add(Runnable r) {
            if (count.get() < maxCount) {
                synchronized (taskLists) {
                    taskLists.add(r);
                    count.getAndIncrement();
                }
            }
        }

        /**
         * 执行一个任务
         *
         * @param r
         */
        public void execute(Runnable r) {
            add(r);
            if (count.get() < maxCount) {
                //从list中拿到最近添加的runnable
                Runnable first = null;
                synchronized (taskLists) {
                    first = taskLists.removeFirst();
                }
                if (first != null) {
                    new Thread(r).start();
                    count.getAndDecrement();
                }
            }
        }

        /**
         * 顺序将list中的任务执行
         */
        public void executeSerial() {
            while (count.get() > 0) {
                Runnable first = null;
                synchronized (taskLists) {
                    first = taskLists.removeFirst();
                }
                if (first != null) {
                    new Thread(first).start();
                }
                count.getAndDecrement();
            }

        }
    }

    /**====================一般性的线程池======================*/

}
