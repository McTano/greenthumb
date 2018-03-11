package com.adiga.greenthumbclient;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements
        UserChoiceFragment.OnUserTypeSelectedListener, RiderChoiceFragment.OnRiderDetailsSelectedListener,
        DriverChoiceFragment.OnDriverDetailsSelectedListener{

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

    private void addRiderFragment() {
        RiderChoiceFragment riderChoiceFragment = new RiderChoiceFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,riderChoiceFragment).commit();
    }

    private void addDriverFragment() {
        DriverChoiceFragment riderChoiceFragment = new DriverChoiceFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,riderChoiceFragment).commit();
    }
    private void setConfirmFragment() {
        ConfirmFragment confirmFragment = new ConfirmFragment();
        confirmFragment.setUser(mUser);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,confirmFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onUserTypeSelected(String type) {
        if (type.equals(UserChoiceFragment.RIDER_CHOICE)) {
            removeChoiceFragment();
            addRiderFragment();
            mUser = new Rider();

        } else if (type.equals(UserChoiceFragment.DRIVER_CHOICE)) {
            removeChoiceFragment();
            addDriverFragment();
            mUser = new Driver();


        }

    }




    @Override
    public void onRiderDetailsSelected(RiderChoiceFragment.RiderInfo info) {
        mUser.setStartLocation(info.getmPlace().getLatLng());
        mUser.setArrivalTime(info.getmTime());
        // TODO make request here
        setConfirmFragment();
    }

    @Override
    public void onDriverDetailsSelected(DriverChoiceFragment.DriverInfo info) {
        mUser.setStartLocation(info.getmPlace().getLatLng());
        mUser.setArrivalTime(info.getmTime());
        Driver driver = (Driver) mUser;

        driver.setCarSeats(info.getNumSeats());
        driver.setLicensePlate(info.getmLicense());

        setConfirmFragment();
    }
}
