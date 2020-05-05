package com.xuwei.myokhttp.frame.chain;

import android.util.Log;

import com.xuwei.myokhttp.frame.OkHttpClient2;
import com.xuwei.myokhttp.frame.Response2;

import java.io.IOException;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:06
 */
public class RetryRequestInterceptor implements Interceptor2 {
    private static final String TAG = "RetryRequestInterceptor";

    @Override
    public Response2 doNext(Chain2 chain) throws IOException {
        Log.d(TAG, "doNext: 我是重试拦截器执行了");
        ChainManager chainManager = (ChainManager) chain;
        IOException exception = null;
        OkHttpClient2 okHttpClient2 = chainManager.getCall().getOkHttpClient2();
        int recount = okHttpClient2.reCount;
        if (recount > 0) {
            for (int i = 0; i < recount; i++) {
                Log.d(TAG, "我是重试拦截器，我要return response了");
                Response2 response = chain.getResponse(chainManager.request());
                return response;

            }
        }
        throw exception;
    }
}
