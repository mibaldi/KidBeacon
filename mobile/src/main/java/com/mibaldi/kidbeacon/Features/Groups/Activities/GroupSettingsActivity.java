package com.mibaldi.kidbeacon.Features.Groups.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.GroupSettingsFragment;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.GroupSingleFragment;
import com.mibaldi.kidbeacon.R;

public class GroupSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);
        OwnGroup ownGroup=getIntent().getParcelableExtra("ownGroup");
        GroupSettingsFragment groupSettingsFragment = GroupSettingsFragment.newInstance(ownGroup);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,groupSettingsFragment).commit();
    }
}
