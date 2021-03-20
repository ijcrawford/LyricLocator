package edu.quinnipiac.lyricapp;

import org.json.JSONException;
import org.json.JSONObject;


public class SongHandler {

    private static final int START_SONG = 76877;
    private static final int END_SONG = 76882;
    public static final String Lyrics = "LYRICS";
    final public static String [] songs = new String[END_SONG - START_SONG +1];;

    public SongHandler(){

        //populate the array
        int i = 0;
        for (int sg = START_SONG; sg <= END_SONG; sg++ )
            songs[i++] = Integer.toString(sg);
    }

    public String getLyrics(String lyricsJsonStr) throws JSONException {
        JSONObject lyricsJSONObj = new JSONObject(lyricsJsonStr);
        return lyricsJSONObj.getString("text");
    }

}