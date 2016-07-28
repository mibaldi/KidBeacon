package com.mibaldi.kidbeacon.Features.Rastreator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSettingsActivity;
import com.mibaldi.kidbeacon.Features.Rastreator.Fragments.ListBeaconsRastreatorFragment;
import com.mibaldi.kidbeacon.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListBeaconsRastreatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beacons_rastreator);
        ButterKnife.bind(this);
        OwnGroup ownGroup=getIntent().getParcelableExtra("ownGroup");
        ListBeaconsRastreatorFragment listBeaconsRastreatorFragment = ListBeaconsRastreatorFragment.newInstance(ownGroup);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,listBeaconsRastreatorFragment).commit();
    }
}
