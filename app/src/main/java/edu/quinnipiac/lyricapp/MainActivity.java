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
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    SongHandler sgHandler = new SongHandler();

    boolean userSelect = false;
    private String url1 = "https://genius.p.rapidapi.com/songs/";
    private String song_id = "76878";
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private ShareActionProvider provider;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);


        ArrayAdapter<String> songsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sgHandler.songs);

        songsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(songsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    final String song = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected :song", song);

                    //TODO : call of async subclass goes here
                    new FetchLyrics().execute(song);
                    userSelect = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;

    }

    class FetchLyrics extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String yearFact = null;
            try{
                URL url = new URL(url1 + song_id);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","UygwA3LnI1mshAPcqbrTdu6rvUkxp1Kd1q6jsnETjeLq2t3LzS");
                urlConnection.setRequestProperty("useQueryString", "true");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if(in == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(in));
                yearFact = getStringFromBuffer(reader);

            }catch (Exception e){
                Log.e(LOG_TAG, "Error" + e.getMessage());
                return null;

            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch(IOException e){
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                        return null;

                    }
                }

            }
            return yearFact;
        }
        @Override
        protected void onPostExecute(String result){
            if(result != null)
                Log.d(LOG_TAG, "Result is null");
            Intent intent = new Intent(MainActivity.this, LyricActivity.class);
            intent.putExtra("Songs", result);
            startActivity(intent);
        }
    }

    private String getStringFromBuffer(BufferedReader reader) {
        StringBuffer buffer = new StringBuffer();
        String line;
        if(reader !=null){
            try{
                while((line = reader.readLine()) != null){
                    buffer.append(line + '\n');
                }
                reader.close();
                return sgHandler.getLyrics(buffer.toString());
            }catch (Exception e){
                Log.e("MainActivity", "Error" + e.getMessage());
                return null;
            }
            finally {

            }
        }
        return null;
    }
}