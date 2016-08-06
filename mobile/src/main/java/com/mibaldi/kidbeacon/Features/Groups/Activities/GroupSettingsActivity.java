package com.mibaldi.kidbeacon.Features.Groups.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupSettingsActivity extends AppCompatActivity {


    @BindView(R.id.groupName)
    EditText groupName;
    @BindView(R.id.groupPhoto)
    EditText groupPhoto;
    private OwnGroup ownGroup;
    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);
        ButterKnife.bind(this);
        ownGroup=getIntent().getParcelableExtra("ownGroup");
        //GroupSettingsFragment groupSettingsFragment = GroupSettingsFragment.newInstance(ownGroup);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,groupSettingsFragment).commit();
        if (ownGroup != null) {
            edit = true;
            groupName.setText(ownGroup.name);
            groupPhoto.setText(ownGroup.photo);
        } else {
            edit = false;
            ownGroup = new OwnGroup();
        }
    }
    @OnClick(R.id.btn_save)
    public void saveGroup() {
        String name = groupName.getText().toString();
        String photo = groupPhoto.getText().toString();

        if (name != "" && photo != "") {
            ownGroup.name = name;
            ownGroup.photo = photo;
            ownGroup.creation_date = new Date().toString();
            if (edit) {
                //FirebaseManager.editGroup(ownGroup);
            } else {

                FirebaseManager.generateGroup(ownGroup);
            }
            finish();
        }

    }
}
