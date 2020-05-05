package com.xuwei.myokhttp.frame;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @Description:
 * @Author: xuwei
 * @CreateDate: 2020/5/4 15:58
 */
public class SocketRequestServer {
    private final String K = " ";
    private final String VIERSION = "HTTP/1.1";
    private final String GRGN = "\r\n";


    /**
     * 通过request2对象获取到域名
     *
     * @param request2
     * @return
     */
    public String getHost(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取端口号
    public int getPort(Request2 request) {
        try {
            URL url = new URL(request.getUrl());
            return url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * todo 获取请求头的所有信息
     * @param request2
     * @return
     */
    public String getRequestHeaderAll(Request2 request2) {
        // 得到请求方式
        URL url = null;
        try {
            url = new URL(request2.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String file = url.getFile();

        // TODO 拼接 请求头 的 请求行  GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request2.getRequestMethod()) // GET or POST
                .append(K)
                .append(file)
                .append(K)
                .append(VIERSION)
                .append(GRGN);

        // TODO 获取请求集 进行拼接
        /**
         * Content-Length: 48\r\n
         * Host: restapi.amap.com\r\n
         * Content-Type: application/x-www-form-urlencoded\r\n
         */
        if (!request2.getHeaderList().isEmpty()) {
            Map<String,String> mapList = request2.getHeaderList();
            for (Map.Entry<String, String> entry: mapList.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append(":").append(K)
                        .append(entry.getValue())
                        .append(GRGN);
            }
            // 拼接空行，代表下面的POST，请求体了
            stringBuffer.append(GRGN);
        }

        // TODO POST请求才有 请求体的拼接
        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            stringBuffer.append(request2.getRequestBody().getBody()).append(GRGN);
        }

        return stringBuffer.toString();
    }

}
