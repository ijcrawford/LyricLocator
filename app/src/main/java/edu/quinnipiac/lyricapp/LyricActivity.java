package edu.quinnipiac.lyricapp;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

        SongDetailFragment frag = (SongDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        frag.setSongUrl(address);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.detail_fragment, new SongDetailFragment());
        ft.commit();

    }

    }

