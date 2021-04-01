package edu.quinnipiac.lyricapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String LOG_TAG = SongListFragment.class.getSimpleName();
    SongHandler sgHandler = new SongHandler();
    private String url1 = "https://genius.p.rapidapi.com/songs/";
    private String resultUrl;



    public SongListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongListFragment newInstance(String param1, String param2) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> songsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,sgHandler.songs);
        songsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(songsAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String song = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected :song", SongHandler.song_ids[position]);

                    new FetchLyrics().execute(SongHandler.song_ids[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getResultUrl() {
        return resultUrl;
    }

    class FetchLyrics extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String yearFact = null;
            try{
                URL url = new URL(url1 + strings[0]);
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
            resultUrl = result;
        }
    }

    private String getStringFromBuffer(BufferedReader reader) {
        StringBuffer buffer = new StringBuffer();
        String line;
        if(reader !=null){
            try{
                line = reader.readLine();
                System.out.println(line);
              /*  while((line = reader.readLine()) != null){
                    buffer.append(line + '\n');

                } */
                reader.close();
                return sgHandler.getLyrics(line);
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