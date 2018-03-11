package com.adiga.greenthumbclient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dbajj on 2018-03-10.
 */

public class ConfirmFragment extends Fragment {

    boolean isDriver;
    User mUser;
    TextView identityText;
    TextView arrivalText;
    Button mapButton;

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
}
