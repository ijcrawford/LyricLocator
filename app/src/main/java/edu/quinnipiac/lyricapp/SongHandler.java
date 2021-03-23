package edu.quinnipiac.lyricapp;

import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.drawable.*;


public class SongHandler {

    private static final int START_SONG = 76877;
    private static final int END_SONG = 76887;
    public static final String Lyrics = "LYRICS";
    final public static String [] songs = {"<select>", "Chandelier", "Into the Black", "Move Back", "Common People", "Progress", "The Ruler", "Creepers", "As You Like It", "Ghetto Love", "Daily Routine"};
    final public static String [] song_ids = {"", "76878","76879", "76880", "76881", "76882","76916","76917","76885","76908","76909"};

    public SongHandler(){

        //populate the array
        /*int i = 0;
        for (int sg = START_SONG; sg <= END_SONG; sg++ )
            songs[i++] = Integer.toString(sg);*/

    }

    public String getLyrics(String lyricsJsonStr) throws JSONException {
        JSONObject resultJSONObj = new JSONObject(lyricsJsonStr);
        JSONObject responseJSONObj = resultJSONObj.getJSONObject("response");
        JSONObject songJSONObj = responseJSONObj.getJSONObject("song");
        return songJSONObj.getString("release_date_for_display");
    }

}