package com.example.administrator.myapplicationjson.uitls;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/1.
 */

public class UrlManager {
    String path = "http://192.168.0.157:8080/hero.json";
    try {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();
        InputStream in = conn.getInputStream();
        final String jsonResult = inputStream2String(in);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
