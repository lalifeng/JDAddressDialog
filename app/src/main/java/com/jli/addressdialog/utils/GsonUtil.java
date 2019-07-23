package com.jli.addressdialog.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jli.addressdialog.entity.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    /**
     * 将json字符串转化为对象
     */
    public static <T> T parseFromStringToBean(String value, Class<T> cla) {
        Gson gson = new Gson();
        return gson.fromJson(value, cla);
    }

    /**
     * 对象转化为Json字符串
     */
    public static String parseFromBeanToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 将获取的字符串转化成List数据源集合
     */
    public static <T> List<T> parseFromStringTolist(String value, Class<T> cla) {
        ArrayList<T> aList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonParser().parse(value).getAsJsonArray();
        for (final JsonElement element : jsonArray) {
            aList.add(gson.fromJson(element, cla));
        }
        return aList;
    }
}
