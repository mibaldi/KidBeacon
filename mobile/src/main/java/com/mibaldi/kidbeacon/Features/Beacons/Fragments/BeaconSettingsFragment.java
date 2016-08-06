package com.mibaldi.kidbeacon.Features.Beacons.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */
public class BeaconSettingsFragment extends Fragment {

    @BindView(R.id.beaconName)
    EditText beaconName;
    private OwnBeacon ownBeacon;
    private OwnGroup ownGroup;
    private boolean edit = false;

    public BeaconSettingsFragment() {
    }

    public static BeaconSettingsFragment newInstance(OwnBeacon ownBeacon,OwnGroup ownGroup) {
        BeaconSettingsFragment fragment = new BeaconSettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable("ownBeacon", ownBeacon);
        args.putParcelable("ownGroup", ownGroup);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beacon_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
    }

}
