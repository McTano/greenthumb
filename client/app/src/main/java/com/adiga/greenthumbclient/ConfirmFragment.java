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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.adiga.greenthumbclient.NetworkUtils.Parser;
import com.google.android.gms.location.places.Place;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dbajj on 2018-03-10.
 */

public class ConfirmFragment extends Fragment implements View.OnClickListener, ListCallback {

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
        String timeOut = "Expected arrival time at UBC is " + parseTime(mUser.getArrivalTime());
        arrivalText.setText(timeOut);
        mapButton.setText("GET YOUR PICKUP ROUTE");
    }

    private String parseTime(String timeInput) {
        boolean pm = false;
        int value = Integer.parseInt(timeInput);

        if (value == 0) {
            return "12:00 am";
        } else if (value == 12) {
            return "12:00 pm";
        }

        if (value > 12) {
            return String.valueOf(value-12) + ":00 pm";
        } else {
            return String.valueOf(value) + ":00 am";
        }
        
    }

    private void setFieldsRider() {
        identityText.setText("You are a RIDER");
        arrivalText.setText("Expected arrival time at UBC is " + mUser.arrivalTime);
        mapButton.setText("GET YOUR PICKUP LOCATION");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_button) {
            Log.d("Confirm","CONFIRMED");

            new QueryTask(this).execute("https://frozen-retreat-24308.herokuapp.com/users/400");

        }


    }

    class QueryTask extends AsyncTask<String, Void, Boolean> {

        List<Pickup> pickups;
        private ListCallback listener;

        public QueryTask(ListCallback listener) {
            this.listener = listener;
        }


        @Override
        protected Boolean doInBackground(String... strings) {
            String urlString = strings[0];

            try {
                pickups = Parser.makePickups(Parser.parseResponse(Parser.makeRequest(urlString)));
            } catch (IOException e) {
                e.printStackTrace();
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            listener.populateList(pickups);

        }


    }



    public void populateList(List<Pickup> pickups) {
        Log.d("Time","To Pare");

        ListView itemList = (ListView) getView().findViewById(R.id.pickup_list);

        List<Pickup> samplePickup = new ArrayList<>();
        samplePickup.add(new Pickup("My House","3:00 pm"));

        ListAdapter adapter = new ListAdapter(pickups);

        itemList.setAdapter(adapter);


    }

    private class ListAdapter extends BaseAdapter {
        List<Pickup> pickups;


        public ListAdapter(List<Pickup> pickups) {
            this.pickups = pickups;
        }


        @Override
        public int getCount() {
            return pickups.size();
        }

        @Override
        public Object getItem(int i) {
            return pickups.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout,viewGroup,false);
            }

            Pickup currentPickup = (Pickup) getItem(i);

            TextView address = (TextView) view.findViewById(R.id.address);
            TextView time = (TextView) view.findViewById(R.id.text_time);

            address.setText(currentPickup.getLocation());
            time.setText(currentPickup.getTime());
            return view;

        }
    }



}

interface ListCallback {
        public void populateList(List<Pickup> pickups);

    }


