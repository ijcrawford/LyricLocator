package edu.quinnipiac.lyricapp;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LyricActivity extends AppCompatActivity {

    final static String url1 = "https://genius.com";
    String myUrl;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_fact);

        /*String fact = (String) getIntent().getExtras().get("yearfact");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(fact);*/
        String path = (String) getIntent().getExtras().get("yearfact");
        String address = url1 + path;

        myWebView = (WebView) findViewById(R.id.mywebview);

        myWebView.setWebViewClient(new MyWebViewClient());

        if(myUrl == null){
            myUrl = address;
        }
        myWebView.loadUrl(myUrl);
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(url);
            return true;
        }
    }

    }

