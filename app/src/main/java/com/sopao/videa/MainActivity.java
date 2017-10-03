package com.sopao.videa;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;


import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;


public class MainActivity extends Activity {

    String name= null;
    String[] temp={"1","2"};
    String[] num={"0","0"};
    GridView list;
    String[] imgurl={"http://api.3ek.com.cn/fuwuqi/sever/%E7%BA%A6%E7%BE%8E.png","http://api.3ek.com.cn/fuwuqi/sever/%E7%BA%A6%E7%BE%8E.png"};
    LazyAdapter adapter;
    private ImageView mRrfresh;
    private LinearLayout mlive;
    private LinearLayout mdianbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlive=(LinearLayout)findViewById(R.id.id_tab_zhibo);
        mdianbo=(LinearLayout)findViewById(R.id.id_tab_dianbo);

        mlive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                aTask ak = new aTask();
                ak.execute();
            }
        });
        mdianbo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);

                startActivity(intent);
            }
        });

mRrfresh=(ImageView)findViewById(R.id.imageView);
        mRrfresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                aTask ak = new aTask();
                ak.execute();
            }
        });
        list = (GridView) findViewById(R.id.gview);
        aTask ak = new aTask();
        ak.execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, LivelistActivity.class);
                intent.putExtra("live", temp[i]);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,"您选择了标题：" + String.valueOf(i), Toast.LENGTH_LONG).show();


            }
        });
    }

    private class aTask extends AsyncTask {

        //后台线程执行时
        @Override
        protected String doInBackground(Object... params) {
            // 耗时操作
            String data = Utils.txtresult("http://api.3ek.com.cn/fuwuqi/sever/name.txt");

            return data;
        }
        //后台线程执行结束后的操作，其中参数result为doInBackground返回的结果
        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            name = result.toString();
            temp=Utils.match(name,"\\S+.(?=\\[)");
            num=Utils.getmidtext(name,"[","]");
            int lengh = temp.length;

            imgurl =new String[lengh];
            for(int i =0; i < lengh; i++) {
                try {
                    imgurl[i]="http://api.3ek.com.cn/fuwuqi/sever/"+ URLEncoder.encode(temp[i],"utf-8")+".png";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            adapter = new LazyAdapter(MainActivity.this, imgurl,temp,num);
            list.setAdapter(adapter);
        }
    }



}
