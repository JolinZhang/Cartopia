package com.example.jonelezhang.cartopia;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Jonelezhang on 6/15/16.
 */
public class JsonParser {
    static InputStream is = null;
    static JSONObject obj = null;
    static String strJson = "";
    static JSONArray jsonObject = null;

    public JsonParser(){

    }
    public String getJsonFromUrl(String url){
        // Make Http request
        try{
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            strJson = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

//        try {
//            Object json = new JSONTokener(strJson).nextValue();
//            if (json instanceof JSONObject){
//                obj = new JSONObject(strJson);
//                return obj;
//            }else if (json instanceof JSONArray){
//                jsonObject = new JSONArray(strJson);
//                return jsonObject;
//            }
//
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }

        // return JSON String
        return strJson;
    }


}
