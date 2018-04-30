package com.example.cynthia.httphelper.Json;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonDispose {
    public static <T> T getObject(@NonNull String json,@NonNull Class<T> cls){
        T object = null;
        try {
            object = cls.newInstance();
            JSONObject object1 = new JSONObject(json);
            Iterator<String> keys = object1.keys();
//          建立一个迭代器对象
            while (keys.hasNext()){
                String key = keys.next();
                String value = object1.getString(key);

//              判断接下来的是内嵌对象还是数组还是单一键值
                if (value.startsWith("{")){
                    Object inner = disposeInner(json,key,cls);
                    ReflectUtil.setValue(object,key,inner);
                }else if (value.startsWith("[")){
                    List<Object> objects = disposeList(object1.getJSONArray(key),key,cls);
                    ReflectUtil.setValue(object,key,objects);
                }else {
                    if (value.equals("null"))
                        ReflectUtil.setValue(object,key,null);
                    else
                        ReflectUtil.setValue(object,key,value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static Object disposeInner(String json,String key,Class cls){
        Class inner = ReflectUtil.getFieldClass(cls,key);
        if (inner != null)
            return getObject(json,inner);

        return null;
    }

    private static List<Object> disposeList(JSONArray array,String key,Class c){
        c = ReflectUtil.getFieldClass(c,key);
        if (c == null)
            return null;
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                String object = array.getString(i);
                if (object.startsWith("{")){
                    list.add(getObject(object,c));
                } else {
                    list.add(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
