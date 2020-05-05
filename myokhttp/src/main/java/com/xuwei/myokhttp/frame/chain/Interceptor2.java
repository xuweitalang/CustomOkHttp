package com.xuwei.myokhttp.frame.chain;

import com.xuwei.myokhttp.frame.Response2;

import java.io.IOException;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:03
 */
public interface Interceptor2 {
    Response2 doNext(Chain2 chain) throws IOException;
}
