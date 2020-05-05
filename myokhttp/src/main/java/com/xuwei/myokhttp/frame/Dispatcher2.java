package com.xuwei.myokhttp.frame;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:35
 */
public class Dispatcher2 {
    private int maxRequests = 64; //同时访问任务，最大64个
    private int maxRequestsPerHost = 5; //同时访问同一个服务器域名，最大限制5个
    private Deque<RealCall2.AsyncCall2> runningAsyncCalls = new ArrayDeque<>(); //存储运行的任务队列
    private Deque<RealCall2.AsyncCall2> readyAsyncCalls = new ArrayDeque<>(); //存储等待队列

    public void enqueue(RealCall2.AsyncCall2 call) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call);

            executorService().execute(call); //执行任务
        } else {
            readyAsyncCalls.add(call);
        }
    }

    //创建线程池
    public ExecutorService executorService() {
        ExecutorService executorService = new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() { //线程工厂
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(false); //设置为不是守护线程
                        thread.setName("自定义的线程。。。");
                        return thread;
                    }
                }

        );
        return executorService;
    }

    //判断AsyncCall2中的Host，在运行的队列中计数
    private int runningCallsForHost(RealCall2.AsyncCall2 call) {
        int count = 0;
        if (runningAsyncCalls.isEmpty()) {
            return 0;
        }
        SocketRequestServer server = new SocketRequestServer();
        for (RealCall2.AsyncCall2 asyncCall :
                runningAsyncCalls) {
            //给host计数
            if (server.getHost(asyncCall.getRequest()).equals(call.getRequest())) {
                count++;
            }
        }

        return count;
    }

    /**
     * 1.回收当前任务
     * 2.将所有等待队列中的任务取出，并执行
     *
     * @param asyncCall2
     */
    public void finished(RealCall2.AsyncCall2 asyncCall2) {
        //将当前运行完的任务回收
        runningAsyncCalls.remove(asyncCall2);
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        for (RealCall2.AsyncCall2 call :
                readyAsyncCalls) {
            readyAsyncCalls.remove(call);
            runningAsyncCalls.add(call); //把刚删除的等待队列加入到运行队列中
            executorService().execute(call);
        }
    }
}
