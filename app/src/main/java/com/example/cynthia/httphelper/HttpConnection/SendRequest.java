package com.example.cynthia.httphelper.HttpConnection;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.example.cynthia.httphelper.Json.JsonDispose;
import com.example.cynthia.httphelper.Response.Callback;
import com.example.cynthia.httphelper.HttpHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class SendRequest<T> extends BasicConnection {

    public static <T> T getResponse(@NonNull final HttpHelper helper, final Class c, final Callback<T> callback) {
        mRequestPool.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    conn = getConnection(helper);
                    final int status = conn.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        final String finish = getStringResponse(conn);
                        UI_HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                T response = (T) JsonDispose.getObject(finish,c);
                                callback.succeed(response);
                                callback.succeed(finish);
                            }
                        });
                    } else {
                        UI_HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.error(new NetworkErrorException("似乎连接出了点问题"), status);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null){
                        conn.disconnect();
                    }
                }
            }
        });
        return null;
    }


    private static String getStringResponse(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }

}
