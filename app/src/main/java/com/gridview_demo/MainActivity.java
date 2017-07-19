package com.gridview_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String jsonStr;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("user", MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        jsonStr = sp.getString("user_setting", null);
        if (jsonStr == null) {
            List<ChannelBean> list = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                ChannelBean bean = null;
                if (i < 5) {
                    bean = new ChannelBean("item-" + i, true);
                } else {
                    bean = new ChannelBean("item-" + i, false);
                }
                list.add(bean);
            }
            ChannelActivity.startChannelActivity(this, list);
        } else {
            ChannelActivity.startChannelActivity(this, jsonStr);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);

            //保存
            sp.edit().putString("user_setting", jsonStr).commit();
        }
    }
}
