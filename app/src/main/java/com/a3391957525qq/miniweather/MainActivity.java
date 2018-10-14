package com.a3391957525qq.miniweather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oemyner on 2018/10/6.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    ImageView imageview1 =null;
    TextView textView1=null;
    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.weather_info);
            imageview1=(ImageView)findViewById(R.id.title_update_btn);
            textView1=(TextView)findViewById(R.id.humidity);
            SharedPreferences.Editor editor=null;
            SharedPreferences pref=null;
            PUT_data(editor,"INPUT_name");
            GET_data(pref,"INPUT_name");
    }
    public void PUT_data(SharedPreferences.Editor editor,String name){
        editor=getSharedPreferences(name,MODE_PRIVATE).edit();
        editor.putInt("nihao",1);
        editor.putFloat("hha",0.02F);
        editor.putString("niubi","cao");
        editor.apply();
    }
    public void GET_data(SharedPreferences pref,String name)
    {
        pref=getSharedPreferences(name,MODE_PRIVATE);
        int a=pref.getInt("nihao",0);
        Float b=pref.getFloat("hha",0.0F);
        String c=pref.getString("niubi","myzhaozhao");
        Log.d("Main",c);
        Log.d("Main",b.toString());
        Log.d("Main",a);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.title_update_btn)
        {
            connect(v);
        }
    }
    public  void connect(View v)
    {
        new Thread(new Runnable() {
            URL url1=null;
            BufferedReader bufferedReader1=null;
            @Override
            public void run() {
                try {
                    url1 = new URL("https//192.168.1.1");
                    HttpURLConnection con = (HttpURLConnection) url1.openConnection();
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    con.setRequestMethod("GET");
                    InputStream inputStream1 = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream1));
                    String s = "";
                    StringBuilder str1 = new StringBuilder();
                    try {
                        while (reader.readLine() != null) {
                            s = reader.readLine();
                            str1.append(s);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        fun2(str1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void fun2(StringBuilder str1)
    {
        str1.toString();
        textView1.setText(str1.toString());
    }
    public void getNetworkState()
    {

    }
    private  void getXML(String a) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xml=factory.newPullParser();
            xml.setInput(new StringReader(a));
            int type=xml.getEventType();
            while(type!=xml.END_DOCUMENT)
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
