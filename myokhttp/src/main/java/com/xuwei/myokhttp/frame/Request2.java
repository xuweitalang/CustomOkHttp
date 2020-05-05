package com.xuwei.myokhttp.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:02
 */
public class Request2 {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private RequestBody2 requestBody;
    String url;
    String requestMethod = GET; //请求方法
    Map<String, String> headerList = new HashMap<>(); //请求集

    public Request2() {
        new Builder();
    }

    public Request2(Builder builder) {
        this.url = builder.url;
        this.requestMethod = builder.requestMethod;
        this.headerList = builder.headerList;
        this.requestBody = builder.requestBody;
    }

    public RequestBody2 getRequestBody() {
        return requestBody;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getHeaderList() {
        return headerList;
    }

    /**
     * 构建者设计模式
     */
    public static class Builder {
        RequestBody2 requestBody;
        String url;
        String requestMethod = GET; //请求方法
        Map<String, String> headerList = new HashMap<>(); //请求集

        public Request2 build() {
            return new Request2(this);
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder addHeader(String key, String value) {
            headerList.put(key, value);
            return this;
        }

        public Builder setHeaderList(HashMap<String, String> headerList) {
            this.headerList = headerList;
            return this;
        }

        public Builder setRequestMethod(String method) {
            this.requestMethod = method;
            return this;
        }

        public Builder setRequestBody(RequestBody2 body) {
            this.requestBody = body;
            return this;
        }

        public Builder get() {
            this.requestMethod = GET;
            return this;
        }

        public Builder post() {
            this.requestMethod = POST;
            return this;
        }
    }

}
