package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String path = "https://www.baidu.com/";
    public static final MediaType JSON
                   = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //同步方法
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               postString1();
//            }
//        }).start();
        //异步方法
       postString2();
    }

    public void getString1() {
        //1.首先创建一个client对象
        OkHttpClient client = new OkHttpClient();
        //2.创建request对象
        Request request = new Request.Builder().url(path).build();
        //3.得到一个call
        Call call = client.newCall(request);
        //4.访问网络
        //一旦调用这个方法，线程就会处于阻塞状态
        try {
            Response response = call.execute();
            Log.e("liangliang", "getString: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getString2() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                       .url(path)
                       .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("liangliang", "getString: " + response.body().string());
                }

            }
        });
    }
    public void postString1(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                       .url(path)
                       .post(body)
                       .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.e("liangliang", "postString: "+response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void postString2(){
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody
                       .Builder()
                       .build();
        Request request = new Request.Builder()
                       .url(path)
                       .post(body)
                       .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("liangliang", "onResponse: 访问网络失败" );

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.e("liangliang", "onResponse: "+response.body().string());
                }else {
                    Log.e("liangliang", "onResponse: 访问网络失败。。。。。。" +response.code());
                }
            }
        });

    }
}
