package com.sopao.videa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.net.URLEncoder;

public class WebViewActivity extends Activity {
private WebView mwebView;
    private Button mbtn;
    private WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mwebView=(WebView)findViewById(R.id.webView);
        mwebView.loadUrl("http://www.iqiyi.com");
         settings = mwebView.getSettings();
       //
        settings.setJavaScriptEnabled(true);

mbtn=(Button)findViewById(R.id.button);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.7 Safari/537.36");
               // mwebView.loadUrl("http://api.baiyug.cn/vip/index.php?url="+ URLEncoder.encode(mwebView.getUrl().replace("m.iqiyi.com","www.iqiyi.com")) );
                Intent intent =new Intent(WebViewActivity.this,WebplayActivity.class);
                intent.putExtra("url","http://api.baiyug.cn/vip/index.php?url="+ URLEncoder.encode(mwebView.getUrl().replace("m.iqiyi.com","www.iqiyi.com")));
                startActivity(intent);
                //Toast.makeText(WebViewActivity.this, mwebView.getUrl(), Toast.LENGTH_LONG).show();
            }
        });


        mwebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view,String url)
            {
                //Toast.makeText(WebViewActivity.this, mwebView.getUrl(), Toast.LENGTH_LONG).show();
            }

        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(mwebView.canGoBack())
            {
                mwebView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
