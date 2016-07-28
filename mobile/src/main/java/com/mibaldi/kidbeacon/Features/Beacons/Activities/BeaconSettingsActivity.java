package com.mibaldi.kidbeacon.Features.Beacons.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Beacons.Fragments.BeaconSettingsFragment;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.GroupSettingsFragment;
import com.mibaldi.kidbeacon.R;

public class BeaconSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_settings);

        OwnBeacon ownBeacon=getIntent().getParcelableExtra("ownBeacon");

        BeaconSettingsFragment beaconSettingsFragment = BeaconSettingsFragment.newInstance(ownBeacon);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,beaconSettingsFragment).commit();
    }
}
