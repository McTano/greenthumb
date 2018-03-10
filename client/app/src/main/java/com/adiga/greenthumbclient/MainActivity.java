package com.adiga.greenthumbclient;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements UserChoiceFragment.OnUserTypeSelectedListener {

    private TextView text;
    private static final int PLACE_PICKER_REQUEST = 1;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createChoiceFragment();

    }

    private void createChoiceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment choiceFragment = new UserChoiceFragment();

        transaction.add(R.id.fragment_container,choiceFragment);
        transaction.commit();
    }

    private void removeChoiceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment choiceFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        transaction.remove(choiceFragment);
        transaction.commit();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String locationString = String.format("Latitude: %f, Longitude: %f",
                        place.getLatLng().latitude, place.getLatLng().longitude);

                text.setText(locationString);
            }
        }
    }

    @Override
    public void onUserTypeSelected(String type) {
        if (type.equals(UserChoiceFragment.RIDER_CHOICE)) {
            removeChoiceFragment();
            mUser = new Rider();

        } else if (type.equals(UserChoiceFragment.DRIVER_CHOICE)) {
            removeChoiceFragment();
            mUser = new Driver();


        }

    }


}
