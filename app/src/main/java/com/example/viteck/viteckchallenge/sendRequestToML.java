package com.example.viteck.viteckchallenge;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;
import static android.os.Debug.waitForDebugger;

/**
 * Created by Eric on 12/3/2017.
 */

interface mlResponse{
    void handleMlResponse(String s);
}

public class sendRequestToML extends AsyncTask<String, Void, String> {
    mlResponse delegate = null;

    @Override
    protected String doInBackground(String[] objects) {
        if(android.os.Debug.isDebuggerConnected()) {
            waitForDebugger();
        }
        String postBody = objects[0];
        String result = "";

        HttpURLConnection urlConnection = null;
        URL url;
        try {
            url = new URL("http://34.201.132.170:5000/get_score");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStream output = urlConnection.getOutputStream();
            output.write(postBody.getBytes("UTF-8"));
            output.flush();
            output.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Get the response message returned by the call
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream response = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    response.write(buffer, 0, length);
                }
                result = response.toString();
                is.close();
                response.close();
            } else {
                //HTTP Error message
                Log.e("HTTP Error", urlConnection.getResponseCode() + urlConnection.getResponseMessage());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        delegate.handleMlResponse(string);
    }
}
