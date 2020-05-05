package com.xuwei.myokhttp.frame;

import com.xuwei.myokhttp.frame.chain.ChainManager;
import com.xuwei.myokhttp.frame.chain.ConnectServerInterceptor;
import com.xuwei.myokhttp.frame.chain.Interceptor2;
import com.xuwei.myokhttp.frame.chain.RequestHeaderInterceptor;
import com.xuwei.myokhttp.frame.chain.RetryRequestInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.platform.Platform;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:27
 */
public class RealCall2 implements Call2 {
    private boolean executed; //标记位，标记是否被执行过
    private OkHttpClient2 okHttpClient2;
    private Request2 request2;

    public RealCall2(OkHttpClient2 okHttpClient2, Request2 request2) {
        this.okHttpClient2 = okHttpClient2;
        this.request2 = request2;
    }

    public OkHttpClient2 getOkHttpClient2() {
        return okHttpClient2;
    }

    public Request2 getRequest2() {
        return request2;
    }

    @Override
    public void enqueue(Callback2 responseCallback) {
        synchronized (this) {
            //不能被重复执行
            if (executed) {
                executed = true;
                throw new IllegalStateException("Already executed");
            }

            okHttpClient2.dispatcher().enqueue(new AsyncCall2(responseCallback));
        }

    }


    public class AsyncCall2 implements Runnable {
        private Callback2 callback;

        public Request2 getRequest() {
            return request2;
        }

        public AsyncCall2(Callback2 callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                if (okHttpClient2.isCanceled()) {
                    signalledCallback = true;
                    callback.onFailure(RealCall2.this, new IOException("Canceled"));
                } else {
                    signalledCallback = true;
                    callback.onResponse(RealCall2.this, response);
                }
            } catch (IOException e) {
                if (signalledCallback) { //如果为true，表示是用户操作导致的错误，不是框架的错误
                    // Do not signal the callback twice!  用户代码导致的错误
                    Platform.get().log(INFO, "Callback failure for userSelf", e);
                } else {
                    callback.onFailure(RealCall2.this, new IOException("okhttp中getResponseWithInterceptorChain 错误" + e.toString()));
                }
            } finally {
                //回收处理
                okHttpClient2.dispatcher().finished(this);
            }
        }

        private Response2 getResponseWithInterceptorChain() {
            List<Interceptor2> interceptorList = new ArrayList<>();
            interceptorList.add(new RetryRequestInterceptor());
            interceptorList.add(new RequestHeaderInterceptor());
            interceptorList.add(new ConnectServerInterceptor());
            ChainManager chainManager = new ChainManager(interceptorList, 0, request2, RealCall2.this);
            return chainManager.getResponse(request2);
        }
    }
}
