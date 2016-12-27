package com.eflake.keyanimengine.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by xueyuanzhang on 16/12/22.
 */

public class JsonUtil {
    private static Gson gson = new Gson();


    public static String readFile(String filePath, Context context) {
        String jsonContent = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
//            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("yinke_moto_static_json.json"), "UTF-8"));
            String singleLine;
            while ((singleLine = reader.readLine()) != null) {
                jsonContent += singleLine;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonContent;
    }

    public static <T> T jsonTobean(String jsonStr, Class<T> beanClass) {
        return gson.fromJson(jsonStr, beanClass);
    }

//    public static String parseJsonStr(String jsonStr) throws Exception{
//        JSONObject jsonObject = new JSONObject(jsonStr);
//        JSONObject animObject = jsonObject.getJSONObject("anim");
//        JSONArray elements = animObject.getJSONArray("elements");
//        for(int i=0;i<elements.length();i++){
//            JSONObject element = elements.getJSONObject(i);
//        }
//    }
}
