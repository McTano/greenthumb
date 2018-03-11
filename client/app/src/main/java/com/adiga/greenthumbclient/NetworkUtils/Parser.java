package com.adiga.greenthumbclient.NetworkUtils;

import android.util.Log;

import com.adiga.greenthumbclient.Pickup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dbajj on 2018-03-10.
 */

public class Parser {
    public static List<Pickup> makePickups(String s) {
        List<Pickup> pickups = new ArrayList<>();
        try {
//            JSONArray stubbed_response = new JSONArray("[[\"6800 Cambie\", \"08:00\"], [\"800 Robson\", \"08:11\"], [\"3780 Arbutus\", \"08:23\"], [\"6133 University\", \"08:42\"]]");
//            JSONArray response = stubbed_response;
            JSONArray response = new JSONArray(s);
            for (int j = 0; j < response.length(); j++) {
                JSONArray jarray = (JSONArray) response.get(j);
                 pickups.add(new Pickup((String) jarray.get(0),
                        (String) jarray.get(1)));
            }
            return pickups;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String parseResponse(BufferedReader in) throws IOException {
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();

        return response.toString();

    }


    public static BufferedReader makeRequest(String urlString) {
        URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            BufferedReader reader =  null;
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int response = urlConnection.getResponseCode();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return reader;
    }
}
