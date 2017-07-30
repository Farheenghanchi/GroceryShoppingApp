package com.farheen.grossary.data;

/**
 * Created by Parth Modi on 19/12/2016.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /********
     * URLS
     *******/
//    private static final String MAIN_URL = "http://gtuimp.com/get_category.php";
    /**
     * TAGs Defined Here...
     */
    public static final String TAG = "TAG";
    /**
     * Key to Send
     */
//    private static final String KEY_USER_ID = "user_id";
    /**
     * Response
     */
    private static Response response;
    /**
     * Get Data From WEB
     *
     * @return JSON Object
     */
    public static JSONObject getDataFromWeb(String str_url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(str_url)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }


}