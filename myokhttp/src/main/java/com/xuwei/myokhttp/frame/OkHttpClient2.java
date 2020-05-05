package com.xuwei.myokhttp.frame;


/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 14:55
 */
public class OkHttpClient2 {
    Dispatcher2 dispatcher2;
    public boolean canceled;
    public int reCount; //重试次数

    public OkHttpClient2() {

    }

    public OkHttpClient2(Builder builder) {
        this.dispatcher2 = builder.dispatcher2;
        this.canceled = builder.canceled;
        this.reCount = builder.reCount;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Dispatcher2 dispatcher() {
        return dispatcher2;
    }


    public Call2 newCall(Request2 request) {
        return new RealCall2(this, request);
    }

    public static final class Builder {
        Dispatcher2 dispatcher2;
        boolean canceled; //用户取消请求
        public int reCount = 3; //重试次数,默认3次

        public Builder() {
            dispatcher2 = new Dispatcher2();
        }

        public OkHttpClient2 build() {
            return new OkHttpClient2(this);
        }

        public Builder canceled() {
            this.canceled = true;
            return this;
        }

        public Builder reCount(int reCount) {
            this.reCount = reCount;
            return this;
        }

        public Builder dispatcher(Dispatcher2 dispatcher2) {
            this.dispatcher2 = dispatcher2;
            return this;
        }
    }


}
