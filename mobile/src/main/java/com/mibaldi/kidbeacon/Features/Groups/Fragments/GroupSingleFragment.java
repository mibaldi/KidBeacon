package com.mibaldi.kidbeacon.Features.Groups.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Beacons.Activities.ListBeaconsActivity;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSettingsActivity;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSingleActivity;
import com.mibaldi.kidbeacon.Features.Groups.Adapters.GroupsListAdapter;
import com.mibaldi.kidbeacon.Features.Rastreator.Activities.ListBeaconsRastreatorActivity;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class GroupSingleFragment extends Fragment  {

    private OwnGroup ownGroup;

    public GroupSingleFragment() {
    }

    public static GroupSingleFragment newInstance(OwnGroup ownGroup) {
        GroupSingleFragment fragment = new GroupSingleFragment();
        Bundle args = new Bundle();
        args.putParcelable("ownGroup", ownGroup);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            ownGroup = getArguments().getParcelable("ownGroup");
        }
    }
    @OnClick(R.id.groupSettings)
    public void groupSettings(){
        Intent intent = new Intent(getActivity(), GroupSettingsActivity.class);
        intent.putExtra("ownGroup",ownGroup);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @OnClick(R.id.beaconList)
    public void beaconList(){
        Intent intent = new Intent(getActivity(), ListBeaconsActivity.class);
        intent.putExtra("ownGroup",ownGroup);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @OnClick(R.id.beaconRastreator)
    public void beaconRastreator(){
        Intent intent = new Intent(getActivity(), ListBeaconsRastreatorActivity.class);
        intent.putExtra("ownGroup",ownGroup);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}