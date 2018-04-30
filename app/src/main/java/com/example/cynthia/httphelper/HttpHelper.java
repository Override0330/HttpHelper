package com.example.cynthia.httphelper;

import android.content.Context;
import android.support.annotation.NonNull;

public class HttpHelper {
    private String mSettingMode;
    private String mUrl;
    private String mParam;
    private int mReadTimeOut;
    private int mConnectTimeOut;

    public static class set {
        private HttpHelper mHelper = new HttpHelper();

        public set() {
            mHelper.mSettingMode = "GET";
            mHelper.mReadTimeOut = 5;
            mHelper.mConnectTimeOut = 10;
        }

        public set mode(@NonNull String mode){
            this.mHelper.mSettingMode = mode;
            return this;
        }

        public set url(@NonNull String url) {
            this.mHelper.mUrl = url;
            return this;
        }

        public set param(String param) {
            this.mHelper.mParam = param;
            return this;
        }

        public set readTO(int s) {
            this.mHelper.mReadTimeOut = s;
            return this;
        }

        public set connectTO(int s) {
            this.mHelper.mConnectTimeOut = s;
            return this;
        }

        public HttpHelper build() {
            return mHelper;
        }
    }

    public String getSettingMode() {
        return mSettingMode;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getParam() {
        return mParam;
    }

    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

}
