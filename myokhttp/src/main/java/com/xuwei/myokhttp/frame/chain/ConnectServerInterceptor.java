package com.xuwei.myokhttp.frame.chain;

import android.util.Log;

import com.xuwei.myokhttp.frame.Request2;
import com.xuwei.myokhttp.frame.Response2;
import com.xuwei.myokhttp.frame.SocketRequestServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @Description: 连接服务器拦截器
 * @Author: xuwei
 * @CreateDate: 2020/5/4 17:57
 */
public class ConnectServerInterceptor implements Interceptor2 {
    private static final String TAG = "ConnectServerIntercepto";

    @Override
    public Response2 doNext(Chain2 chain) throws IOException {
        SocketRequestServer server = new SocketRequestServer();
        Request2 request = chain.request();
        Socket socket = new Socket(server.getHost(request), server.getPort(request));

        //请求
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        String headerAll = server.getRequestHeaderAll(request);
        Log.d(TAG, headerAll);
        bufferedWriter.write(headerAll); //给服务器发送请求
        bufferedWriter.flush();

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                String readLine = null;
//                while (true) {
//                    try {
//                        if ((readLine = bufferedReader.readLine()) != null) {
//                            Log.d(TAG, "服务器响应的：" + readLine);
//                        } else {
//                            return;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        Response2 response2 = new Response2();

        //  取出 响应码
        String readLine = bufferedReader.readLine(); // 读取第一行 响应头信息
        // 服务器响应的:HTTP/1.1 200 OK
        String[] strings = readLine.split(" ");
        response2.setStatusCode(Integer.parseInt(strings[1]));

        //  取出响应体，只要是空行下面的，就是响应体
        String readerLine = null;
        try {
            while ((readerLine = bufferedReader.readLine()) != null) {
                if ("".equals(readerLine)) {
                    // 读到空行了，就代表下面就是 响应体了
                    response2.setBody(bufferedReader.readLine());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Response2 response2 = new Response2();
//        response2.setBody("流程走通。。。");
        return response2;
    }
}
