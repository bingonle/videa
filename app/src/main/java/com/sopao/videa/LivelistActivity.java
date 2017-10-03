package com.sopao.videa;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import java.net.URLEncoder;

public class LivelistActivity extends Activity {

    private String name=null;
    private GridView list;
    private LazyAdapter2 adapter;
    private String[] imgurl;
    private String[] room;
    private String[] playurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelist);
 name=getIntent().getStringExtra("live");
        list = (GridView) findViewById(R.id.gview);
        aTask ak=new aTask();
        ak.execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LivelistActivity.this, NEVideoPlayerActivity.class);
                //把多个参数传给NEVideoPlayerActivity
                intent.putExtra("media_type", "livestream");
                intent.putExtra("decode_type", "videoondemand");
                intent.putExtra("videoPath", playurl[i]);
                intent.putExtra("name", room[i]);
                startActivity(intent);

            }
        });
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(listener);

    }
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            LivelistActivity.this.finish();
            // Intent intent=new Intent(LivelistActivity.this,MainActivity.class);
             //intent.putExtra("live",playurl[i]);
           // startActivity(intent);
        }
    };
    private class aTask extends AsyncTask {

        //后台线程执行时
        @Override
        protected String doInBackground(Object... params) {
            // 耗时操作
            String data = Utils.txtresult("http://api.3ek.com.cn/fuwuqi/sever/"+URLEncoder.encode(name)+".txt");
            return data;
        }
        //后台线程执行结束后的操作，其中参数result为doInBackground返回的结果
        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            name = result.toString();
            name=Utils.RC4decode( name,"zuozheqq76912376@pojiegun");
            imgurl=Utils.getmidtext("\n"+name,"\n|","|#");
            room=Utils.getmidtext(name,"|#","#@");
            playurl=Utils.getmidtext(name,"#@","@");
            adapter = new LazyAdapter2(LivelistActivity.this, imgurl,room,playurl);
            list.setAdapter(adapter);


        }
    }
}
