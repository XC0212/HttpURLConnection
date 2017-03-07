package com.example.administrator.myapplicationjson.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplicationjson.R;
import com.example.administrator.myapplicationjson.entity.Hero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv_show;
    private Button mBtn_URL;
    private TextView mTv_show;
    private Button mBtn_commit;
    private Button mBtn_array;
    private List<Hero> heros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_show = (TextView) findViewById(R.id.tv_show);
        mBtn_commit = (Button) findViewById(R.id.btn_commid);
        mBtn_array = (Button) findViewById(R.id.btn_array);
        mBtn_URL = (Button) findViewById(R.id.btn_url);
        mIv_show = (ImageView) findViewById(R.id.iv_show);
//__________________________________________________________________
//        mBtn_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String jsonObject = "{\n" +
//                        "\t\"name\":\"臧三\",\n" +
//                        "\t\"age\":21\n" +
//                        "}";
//                try {
//                    JSONObject object = new JSONObject(jsonObject);
//                    String name = (String) object.get("name");
//                    int age = object.getInt("age");
//                    mTv_show.setText(name + "+" + age);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//_________________________________________________________________________________________
//        mBtn_array.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String jsonArray = "[\n" +
//                        "\t{\n" +
//                        "\t\t\"name\":\"zangsan\",\n" +
//                        "\t\t\"age\":22\n" +
//                        "\t},\n" +
//                        "\t{\n" +
//                        "\t\t\"name\":\"lisi\",\n" +
//                        "\t\t\"age\":21\n" +
//                        "\t},\n" +
//                        "\t{\n" +
//                        "\t\t\"name\":\"wangwu\",\n" +
//                        "\t\t\"age\":20\n" +
//                        "\t}\n" +
//                        "]";
//                try {
//
//                    JSONArray array = new JSONArray(jsonArray);
//                    StringBuffer sb = new StringBuffer();
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = (JSONObject) array.get(i);
//                        String name = (String) object.get("name");
//                        int age = object.getInt("age");
//                        sb.append(name + "," + age + "\n");
//                    }
//                    mTv_show.setText(sb.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//__________________________________________________________________________________________
        mBtn_URL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                }).start();

            }

        });

    }

    private String inputStream2String(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        try {
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String(baos.toByteArray());
    }


    public List<Hero> parseJson(String jsonResult) {
        List<Hero> heros = new ArrayList<Hero>();
        try {
            //先解析JSON大对象
            JSONObject object = new JSONObject(jsonResult);
            //通过拿到大对象中heros的键拿到heros数组
            JSONArray array = (JSONArray) object.get("heros");
            //遍历数组内容
            for (int i = 0; i < array.length(); i++) {
                //拿到数组中hero的对象
                JSONObject o = (JSONObject) array.get(i);
                //通过对象拿到对象属性
                String name = (String) o.get("name");
                String des = (String) o.get("des");
                String icon = (String) o.get("iconUrl");
                String path = icon;
                //解析图片
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();
                    InputStream in = conn.getInputStream();
                    BitmapDrawable bd=new BitmapDrawable(in);
                    //将解析出的属性传给Hero
                    Hero hero = new Hero(name, des, bd);
                    //想heros集合中添加Hero
                    heros.add(hero);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return heros;
    }

//    public void bitmap(View v) {
//        new Thread(new Runnable() {
//
//
//
//            @Override
//            public void run() {
//
//               // String path = icon;
//                try {
//                    URL url = new URL(path);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(5000);
//                    conn.setReadTimeout(5000);
//                    conn.connect();
//                    InputStream in = conn.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(in);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            mIv_show.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }

}


