package com.xuwei.myokhttp.frame.chain;

import com.xuwei.myokhttp.frame.Request2;
import com.xuwei.myokhttp.frame.RequestBody2;
import com.xuwei.myokhttp.frame.Response2;
import com.xuwei.myokhttp.frame.SocketRequestServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 请求头拦截器处理
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:42
 */
public class RequestHeaderInterceptor implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain) throws IOException {
        ChainManager chainManager = (ChainManager) chain;
        Request2 request = chainManager.getRequest();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", new SocketRequestServer().getHost(chainManager.request()));
        if ("POST".equalsIgnoreCase(chainManager.request().getRequestMethod())) {
            headerMap.put("Content-Length", request.getRequestBody().getBody());
            headerMap.put("Content-Type", RequestBody2.TYPE);
        }
        return chain.getResponse(request); //执行下一个拦截器
    }
}
