package com.a3391957525qq.miniweather;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.zhoushengyang.app.MyApplication;
import cn.edu.pku.zhoushengyang.bean.City;
import cn.edu.pku.zhoushengyang.util.NetUtil;

import static com.a3391957525qq.miniweather.R.id.city;

/**
 * Created by oemyner on 2018/10/23.
 */
public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    private ListView mlistView;
    private MyApplication myApplication;
    private List<City> cityList;
    private List<String> res_id;
    private List<String> res_name;
    private EditText editText;
    private Button button;
    private TextView textview;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_city);
        editor=getSharedPreferences("config",MODE_PRIVATE).edit();
        textview = (TextView) findViewById(R.id.title_name);
        Intent intent = getIntent();
        textview.setText(intent.getStringExtra("cityCode"));
        button = (Button) findViewById(R.id.button10);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        editText = (EditText) findViewById(R.id.aba);
        button.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        res_id = new ArrayList<String>();
        res_name = new ArrayList<String>();
        myApplication = (MyApplication) getApplication();
        cityList = myApplication.getCityList();
        for (City city : cityList) {
            res_name.add(city.getCity());
        }
        mlistView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SelectCity.this, android.R.layout.simple_list_item_1, res_name);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SelectCity.this, "你单击了:" + position + 1, Toast.LENGTH_SHORT).show();
                City city = cityList.get(position);
                Intent i = new Intent();
                i.putExtra("cityCode", city.getNumber());
                editor.putString("main_city_code",city.getNumber());
                editor.commit();
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        List<City> list = new ArrayList<>();
        switch (v.getId()) {
            case R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode", "101010100");
                setResult(RESULT_OK, i);
                finish();
                break;
            case R.id.button10:
                if (editText.getText().toString().equals("")) {
                    clear();
                    for (City city : cityList) {
                        res_name.add(city.getCity());
                    }
                    mlistView.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, res_name));
                } else {
                    clear();
                    for (City city : cityList) {
                        if (city.getCity().contains(editText.getText().toString())) {
                            list.add(city);
                            res_name.add(city.getCity());
                        }
                    }
                    mlistView.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, res_name));
                    cityList = list;

                }
                break;
            default:
                break;
        }
    }
    public void clear() {
        ArrayAdapter adapter = (ArrayAdapter) mlistView.getAdapter();
        res_name.clear();
        int count = adapter.getCount();
        if (count > 0) {
            mlistView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1));
        }
    }

}