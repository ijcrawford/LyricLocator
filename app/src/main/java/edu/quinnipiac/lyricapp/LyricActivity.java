package edu.quinnipiac.lyricapp;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LyricActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_fact);

        String fact = (String) getIntent().getExtras().get("yearfact");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(fact);

    }
}
