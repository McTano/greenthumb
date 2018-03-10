package com.adiga.greenthumbclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class UserChoiceFragment extends Fragment implements View.OnClickListener {
    public static final String DRIVER_CHOICE = "driver";
    public static final String RIDER_CHOICE = "rider";
    Button mRiderButton;
    Button mDriverButton;
    private OnUserTypeSelectedListener mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_type_fragment,container, false);
        mRiderButton = view.findViewById(R.id.rider_button);
        mDriverButton= view.findViewById(R.id.driver_button);

        mRiderButton.setOnClickListener(this);
        mDriverButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnUserTypeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnUserTypeSelectedListener");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case(R.id.driver_button):
                mListener.onUserTypeSelected(DRIVER_CHOICE);
                break;
            case(R.id.rider_button):
                mListener.onUserTypeSelected(RIDER_CHOICE);
                break;
        }

    }

    public interface OnUserTypeSelectedListener {
        public void onUserTypeSelected(String type);
    }
}


