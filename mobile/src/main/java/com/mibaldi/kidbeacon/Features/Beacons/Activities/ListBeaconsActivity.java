package com.mibaldi.kidbeacon.Features.Beacons.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Beacons.Fragments.ListBeaconsFragment;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSettingsActivity;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.ListGroupsFragment;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListBeaconsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beacons);
        OwnGroup ownGroup=getIntent().getParcelableExtra("ownGroup");
        ListBeaconsFragment listBeaconsFragment = ListBeaconsFragment.newInstance(ownGroup);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,listBeaconsFragment).commit();
    }

}
