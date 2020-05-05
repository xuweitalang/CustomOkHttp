package com.xuwei.myokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xuwei.myokhttp.frame.Call2;
import com.xuwei.myokhttp.frame.Callback2;
import com.xuwei.myokhttp.frame.OkHttpClient2;
import com.xuwei.myokhttp.frame.Request2;
import com.xuwei.myokhttp.frame.Response2;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final String PATH = "http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myOKHttp(View view) {
    }

    public void otherOKHttp(View view) {

        OkHttpClient2 okHttpClient2 = new OkHttpClient2.Builder().build();
        Request2 request2 = new Request2.Builder().setUrl(PATH).build();
        Call2 call2 = okHttpClient2.newCall(request2);
        call2.enqueue(new Callback2() {
            @Override
            public void onFailure(Call2 call, IOException e) {
                Log.e(TAG, "自定义OKhttp请求失败");
            }

            @Override
            public void onResponse(Call2 call, Response2 response) throws IOException {
                Log.e(TAG, "自定义OKhttp请求成功=" + response.toString());
            }
        });
    }
}
