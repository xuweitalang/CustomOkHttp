package com.xuwei.myokhttp.frame;

import java.io.IOException;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:00
 */
public interface Callback2 {

    void onFailure(Call2 call, IOException e);

    void onResponse(Call2 call, Response2 response) throws IOException;
}
