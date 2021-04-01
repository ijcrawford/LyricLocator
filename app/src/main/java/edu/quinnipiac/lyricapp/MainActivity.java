package edu.quinnipiac.lyricapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static edu.quinnipiac.lyricapp.SongHandler.song_ids;

public class MainActivity extends AppCompatActivity {

    private String url1 = "https://genius.p.rapidapi.com/songs/";
    private String song_id = "76878";
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private ShareActionProvider provider;
    private SongListFragment slFrag;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongListFragment frag = (SongListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        slFrag = new SongListFragment();
        ft.add(R.id.list_fragment, slFrag);
        ft.commit();
    }

    public void sendToDetail(String result) {
        Intent intent = new Intent(MainActivity.this, LyricActivity.class);
        intent.putExtra("yearfact", result);
        startActivity(intent);
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        sendToDetail(slFrag.getResultUrl());

    }

   }