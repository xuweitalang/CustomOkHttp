package com.xuwei.myokhttp.frame.chain;

import com.xuwei.myokhttp.frame.RealCall2;
import com.xuwei.myokhttp.frame.Request2;
import com.xuwei.myokhttp.frame.Response2;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 责任链管理类：等同于OKhttp中RealInterceptorChain的功能
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:08
 */
public class ChainManager implements Chain2 {
    private final List<Interceptor2> interceptors;
    private int index;
    private final Request2 request;
    private final RealCall2 call;

    public ChainManager(List<Interceptor2> interceptors, int index, Request2 request, RealCall2 call) {
        this.interceptors = interceptors;
        this.request = request;
        this.call = call;
        this.index = index;
    }

    public List<Interceptor2> getInterceptors() {
        return interceptors;
    }

    public int getIndex() {
        return index;
    }

    public Request2 getRequest() {
        return request;
    }

    public RealCall2 getCall() {
        return call;
    }

    @Override
    public Request2 request() {
        return request;
    }

    @Override
    public Response2 getResponse(Request2 request) {
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }
        //取出第一个拦截器
        Interceptor2 interceptor = interceptors.get(index);
        ChainManager chainManager = new ChainManager(interceptors, index + 1, request, call);
        Response2 response2 = null;
        try {
            response2 = interceptor.doNext(chainManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response2;
    }
}
