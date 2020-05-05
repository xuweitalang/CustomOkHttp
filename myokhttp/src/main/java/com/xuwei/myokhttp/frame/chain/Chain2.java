package com.xuwei.myokhttp.frame.chain;

import com.xuwei.myokhttp.frame.Request2;
import com.xuwei.myokhttp.frame.Response2;

import java.io.IOException;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:03
 */
public interface Chain2 {

    Request2 request();

    Response2 getResponse(Request2 request);
}
