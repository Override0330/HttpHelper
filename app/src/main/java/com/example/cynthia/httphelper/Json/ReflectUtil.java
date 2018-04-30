package com.example.cynthia.httphelper.Json;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectUtil {

    /**
     * value赋值给object对象的key属性
     *
     * @param object 传入对象
     * @param key    对应属性
     * @param value  相应键值
     */
    public static void setValue(@NonNull Object object, @NonNull String key, Object value) {
        try {
            Class cls = object.getClass();
            Field field = cls.getDeclaredField(key);
            field.setAccessible(true);
            if (value == null){
                field.set(object, value);
            } else if (value.equals("false")) {
                field.set(object, false);
            } else if (value.equals("true")) {
                field.set(object, true);
            } else {
                field.set(object, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取内嵌的class类型（譬如List<T> mList中的T类型）
     *
     * @param cls       外层class
     * @param fieldName List对应的名称
     * @return 内层T的名称
     */

    public static Class getFieldClass(Class cls, String fieldName) {
        Field f = null;
        try {
            f = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (f == null)
            return null;
        Class fieldClass = f.getType();
        if (fieldClass.isAssignableFrom(List.class)) {
            //获取list的泛型类型
            Type type = f.getGenericType();
            if (type != null && type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                fieldClass = (Class) parameterizedType.getActualTypeArguments()[0];
            }
        }
        return fieldClass;
    }

}
