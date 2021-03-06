package com.adiga.greenthumbclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dbajj on 2018-03-10.
 */

public class RiderChoiceFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Spinner mSpinner;
    Button mLocationButton;
    Button mConfirmButton;

    private Place mPlace;
    private boolean timeSelected;

    private static final int PLACE_PICKER_REQUEST = 1;
    OnRiderDetailsSelectedListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        timeSelected = false;

        View layout = inflater.inflate(R.layout.rider_choice_fragment,container, false);

        mLocationButton = (Button) layout.findViewById(R.id.setlocationbutton);
        mLocationButton.setOnClickListener(this);

        mConfirmButton = (Button) layout.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnRiderDetailsSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnUserTypeSelectedListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupSpinner();
    }

    private void setupSpinner() {
        mSpinner = (Spinner) getView().findViewById(R.id.time_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.time_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() ==  R.id.setlocationbutton) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        } else if (view.getId() == R.id.confirm_button) {
            if (mPlace == null || timeSelected == false) {
                Toast.makeText(getContext(),"Please select your time and location before confirming",Toast.LENGTH_SHORT).show();
            } else {
                String time = mSpinner.getSelectedItem().toString();
                String simpleTime = parseTime(time);
                mListener.onRiderDetailsSelected(new RiderInfo(mPlace,simpleTime));
            }
        }
    }

    private String parseTime(String time) {
        Pattern p = Pattern.compile(".*am");
        Matcher timeMatcher = p.matcher(time);

        if (timeMatcher.matches()) {
            return time.substring(0,1);
        } else {
            Integer timeInt = Integer.parseInt(time.substring(0,1));
            return String.valueOf(timeInt+12);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                mPlace = PlacePicker.getPlace(getActivity(), data);
                String location = mPlace.getAddress().toString();
                Toast.makeText(getContext(), "RECEIVED", Toast.LENGTH_LONG).show();
                mLocationButton.setText(location);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        timeSelected = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnRiderDetailsSelectedListener {

        public void onRiderDetailsSelected(RiderInfo info);

    }

    public class RiderInfo {
        private Place mPlace;
        private String mTime;

        public RiderInfo(Place place, String time) {
            this.mPlace = place;
            this.mTime = time;
        }

        public Place getmPlace() {
            return mPlace;
        }

        public String getmTime() {
            return mTime;
        }
    }
}
