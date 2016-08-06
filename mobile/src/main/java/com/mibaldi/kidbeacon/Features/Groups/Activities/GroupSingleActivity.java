package com.mibaldi.kidbeacon.Features.Groups.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.GroupSingleFragment;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.ListGroupsFragment;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_single);
        OwnGroup ownGroup=getIntent().getParcelableExtra("ownGroup");
        GroupSingleFragment groupSingleFragment = GroupSingleFragment.newInstance(ownGroup);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,groupSingleFragment).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
