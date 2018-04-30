package com.example.cynthia.httphelper.HttpConnection;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.cynthia.httphelper.HttpHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 进行网络请求的通用操作
 */

class BasicConnection {

    static final Handler UI_HANDLER = new Handler();
//  主线程
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
//  获取设备的cpu核心数，据说有些时候不符合实际要求
    static ExecutorService mRequestPool = new ThreadPoolExecutor(THREAD_COUNT,
            THREAD_COUNT, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
//  创建一个fixThreadPool，只有核心线程，不会被回收，能够快速响应外界请求。
//  对应参数：核心线程数，最大线程数，超时时长，枚举类型（设置keepAlive的单位和时长），缓冲任务队列）

    static HttpURLConnection getConnection(@NonNull HttpHelper helper) throws IOException {
        URL mUrl = new URL(helper.getUrl());
        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
        conn.setReadTimeout(helper.getReadTimeOut() * 1000);
        conn.setConnectTimeout(helper.getConnectTimeOut() * 1000);
        conn.setDoInput(true);
        if (helper.getSettingMode().equals("POST")) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(helper.getParam().getBytes("UTF-8"));
            os.flush();
            os.close();
        }
        return conn;
    }
}
