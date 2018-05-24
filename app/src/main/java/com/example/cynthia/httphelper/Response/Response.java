package com.example.cynthia.httphelper.Response;

import android.support.annotation.NonNull;

import com.example.cynthia.httphelper.HttpConnection.SendRequest;
import com.example.cynthia.httphelper.HttpHelper;

public class Response {
    private HttpHelper helper;
    private Class mClass = null;

    public static class from{
        private Response mResponse = new Response();
        public from(HttpHelper helper){
            this.mResponse.helper = helper;
        }

        public from to(@NonNull Class c){
            this.mResponse.mClass = c;
            return this;
        }

        public Response get(Callback callback){
            SendRequest.getResponse(mResponse.helper,mResponse.mClass,callback);
            return mResponse;
        }
    }
}
