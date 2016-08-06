package com.mibaldi.kidbeacon.Features.Beacons.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeaconSettingsActivity extends AppCompatActivity {
    private boolean edit = false;
    @BindView(R.id.beaconName)
    EditText beaconName;
    @BindView(R.id.beaconUUID)
    EditText beaconUUID;
    @BindView(R.id.beaconMajor)
    EditText beaconMajor;
    @BindView(R.id.beaconMinor)
    EditText beaconMinor;
    private OwnBeacon ownBeacon;
    private OwnGroup ownGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_settings);
        ButterKnife.bind(this);
        ownGroup =getIntent().getParcelableExtra("ownGroup");
        ownBeacon =getIntent().getParcelableExtra("ownBeacon");
        if (ownBeacon != null) {
            edit = true;
            beaconName.setText(ownBeacon.name);
            beaconUUID.setText(ownBeacon.uuid);
            beaconMajor.setText(ownBeacon.major);
            beaconMinor.setText(ownBeacon.minor);
        }else {
            edit=false;
            ownBeacon = new OwnBeacon();
        }
        //BeaconSettingsFragment beaconSettingsFragment = BeaconSettingsFragment.newInstance(ownBeacon,ownGroup);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,beaconSettingsFragment).commit();
    }
    @OnClick(R.id.btn_save)
    public void saveGroup() {
        String name = beaconName.getText().toString();
        String UUID = beaconUUID.getText().toString();
        String major = beaconMajor.getText().toString();
        String minor = beaconMinor.getText().toString();

        if (name != "") {
            ownBeacon.name = name;
            ownBeacon.uuid = UUID;
            ownBeacon.major = major;
            ownBeacon.minor = minor;
            if (edit) {
                //FirebaseManager.editBeacon(ownBeacon);
            } else {

                FirebaseManager.generateBeacon(ownBeacon,ownGroup.id);
            }
            finish();
        }

    }
}
