package com.adiga.greenthumbclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class DriverChoiceFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    Spinner mSpinner;
    Button mLocationButton;
    Button mConfirmButton;

    EditText mLicenseText;
    Spinner mSeats;

    private Place mPlace;
    private int mSpinnerCount;
    private boolean timeSelected;
    private boolean licenseAdded;
    private boolean seatsSelected;
    private TextWatcher mLicenseWatcher;


    private static final int PLACE_PICKER_REQUEST = 1;
    OnDriverDetailsSelectedListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSpinnerCount = 0;

        timeSelected = false;
        licenseAdded = false;
        seatsSelected = false;

        View layout = inflater.inflate(R.layout.driver_choice_fragment,container, false);

        mLocationButton = (Button) layout.findViewById(R.id.setlocationbutton);
        mLocationButton.setOnClickListener(this);

        mConfirmButton = (Button) layout.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(this);

        mLicenseText = (EditText) layout.findViewById(R.id.plate_text);


        mLicenseText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                licenseAdded = true;

            }
        });

        return layout;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDriverDetailsSelectedListener) context;
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
        mSeats = (Spinner) getView().findViewById(R.id.seat_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_seats = ArrayAdapter.createFromResource(getContext(), R.array.seat_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mSeats.setAdapter(adapter_seats);
        mSeats.setOnItemSelectedListener(this);
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
            if (mPlace == null || timeSelected == false || seatsSelected ==  false || licenseAdded == false) {
                String warning = "Please select all fields before confirming";
                String debug = "timeSelected " + timeSelected + " seatsSelected: " + seatsSelected +
                        "licenseAdded: " + licenseAdded + " mPlace: " + mPlace;
                Toast.makeText(getContext(),warning + debug,Toast.LENGTH_SHORT).show();
            } else {
                String time = mSpinner.getSelectedItem().toString();
                String simpleTime = parseTime(time);
                String license = mLicenseText.getText().toString();
                int numSeats = Integer.parseInt(mSeats.getSelectedItem().toString());
                mListener.onDriverDetailsSelected(new DriverInfo(mPlace,simpleTime,license,numSeats));
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
                mLocationButton.setText(location);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (mSpinnerCount < 2) {
            mSpinnerCount++;
            return;
        }
        if (adapterView.getId() == R.id.time_spinner) {
            timeSelected = true;
        } else if (adapterView.getId() == R.id.seat_spinner) {
            seatsSelected = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnDriverDetailsSelectedListener {

        public void onDriverDetailsSelected(DriverInfo info);

    }

    public class DriverInfo {
        private Place mPlace;
        private String mTime;
        private String mLicense;
        private int numSeats;

        public DriverInfo(Place place, String time, String mLicense, int numSeats) {
            this.mPlace = place;
            this.mTime = time;
            this.mLicense = mLicense;
            this.numSeats = numSeats;
        }

        public Place getmPlace() {
            return mPlace;
        }

        public String getmTime() {
            return mTime;
        }

        public String getmLicense() {
            return mLicense;
        }

        public int getNumSeats() {
            return numSeats;
        }
    }
}
