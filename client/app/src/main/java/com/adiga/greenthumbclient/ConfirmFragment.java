package com.adiga.greenthumbclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adiga.greenthumbclient.NetworkUtils.Parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dbajj on 2018-03-10.
 */

public class ConfirmFragment extends Fragment implements View.OnClickListener {

    boolean isDriver;
    User mUser;
    TextView identityText;
    TextView arrivalText;
    Button mapButton;
    List<String> addresses;
    List<String> pickup_times;

    public ConfirmFragment() {

    }

    public void setUser(User user) {
        this.mUser = user;
        if (mUser instanceof Driver) {
            isDriver = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.confirm_fragment,container,false);

        identityText = (TextView) view.findViewById(R.id.identity_text);
        arrivalText = (TextView) view.findViewById(R.id.arrival_text);

        mapButton = (Button) view.findViewById(R.id.map_button);
        mapButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (isDriver) {
            setFieldsDriver();
        } else {
            setFieldsRider();
        }
    }

    private void setFieldsDriver() {
        identityText.setText("You are a DRIVER");
        arrivalText.setText("Expected arrival time at UBC is " + mUser.arrivalTime);
        mapButton.setText("GET YOUR PICKUP ROUTE");
    }

    private void setFieldsRider() {
        identityText.setText("You are a RIDER");
        arrivalText.setText("Expected arrival time at UBC is " + mUser.arrivalTime);
        mapButton.setText("GET YOUR PICKUP LOCATION");
    }

    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Log.d("Confirm","CONFIRMED");

        }



    }

    class QueryTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String urlString = strings[0];

            try {
                String pickups = Parser.parseResponse(Parser.makeRequest(urlString));
            } catch (IOException e) {
                e.printStackTrace();
            }


            return true;
        }
    }


}

