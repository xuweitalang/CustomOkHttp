package com.xuwei.myokhttp.frame;

import android.support.annotation.NonNull;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:02
 */
public class Response2 {
    String body;
    private int statusCode;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return body;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
