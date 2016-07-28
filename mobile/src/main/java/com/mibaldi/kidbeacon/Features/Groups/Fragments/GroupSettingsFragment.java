package com.mibaldi.kidbeacon.Features.Groups.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSettingsActivity;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */
public class GroupSettingsFragment extends Fragment {

    @BindView(R.id.groupName)
    EditText groupName;
    private OwnGroup ownGroup;


    public GroupSettingsFragment() {
    }

    public static GroupSettingsFragment newInstance(OwnGroup ownGroup) {
        GroupSettingsFragment fragment = new GroupSettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable("ownGroup", ownGroup);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            ownGroup = getArguments().getParcelable("ownGroup");
        }
        if (ownGroup != null){
            groupName.setText(ownGroup.name);
        }
    }

}
