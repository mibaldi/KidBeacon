package com.mibaldi.kidbeacon.Features.Beacons.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Beacons.Activities.BeaconSettingsActivity;
import com.mibaldi.kidbeacon.Features.Beacons.Adapters.ListBeaconsAdapter;
import com.mibaldi.kidbeacon.Features.Beacons.NFC.NfcActivity;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSingleActivity;
import com.mibaldi.kidbeacon.Features.Groups.Adapters.GroupsListAdapter;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import timber.log.Timber;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class ListBeaconsFragment extends Fragment  implements ListBeaconsAdapter.OnItemClickListener {
    @BindView(R.id.beaconsList  )
    RecyclerView recyclerView;

    private List<OwnBeacon> items = new ArrayList<>();;
    private OwnGroup ownGroup;
    private ListBeaconsAdapter adapter;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public ListBeaconsFragment() {
    }

    public static ListBeaconsFragment newInstance(OwnGroup ownGroup ) {
        ListBeaconsFragment fragment = new ListBeaconsFragment();
        Bundle args = new Bundle();
        args.putParcelable("ownGroup", ownGroup);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beacon_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            ownGroup = getArguments().getParcelable("ownGroup");
        }

        items = new ArrayList<>();
        adapter = new ListBeaconsAdapter(items,ListBeaconsFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getBeacons();
        loadSwipeRefreshLayout();
    }

    @Override
    public void onItemClick(View view, OwnBeacon ownBeacon) {
        Intent intent = new Intent(getActivity(), BeaconSettingsActivity.class);
        intent.putExtra("ownBeacon",ownBeacon);
        startActivity(intent);
    }
    @OnClick(R.id.fab2)
    public void addBeacon(){
        Intent intent = new Intent(getActivity(),BeaconSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ownGroup",ownGroup);
        this.startActivity(intent);
    }
    @OnClick(R.id.fab3)
    public void addBeaconNFC(){
        Intent intent = new Intent(getActivity(),NfcActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ownGroup",ownGroup);
        this.startActivity(intent);
    }
    private void loadSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBeacons();
            }
        });
    }

    public void getBeacons() {
        items.clear();
        AlertDialog dialog = new SpotsDialog(getActivity(),"Cargando Beacons");

        dialog.show();
        RxFirebaseDatabase.observeValuesList(FirebaseManager.refGroups.child(ownGroup.id+"/beacons"), String.class)
                .subscribe(groupBeacons -> {
                    for (String key : groupBeacons) {
                        RxFirebaseDatabase.observeSingleValue(FirebaseManager.refBeacons.child(key), OwnBeacon.class)
                                .subscribe(ownBeacon -> {
                                    items.add(ownBeacon);
                                    dialog.hide();
                                    adapter.notifyDataSetChanged();
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }, throwable -> {
                                    Timber.e("ListBeaconsFragment", throwable.toString());
                                });
                    }
                }, throwable -> {
                    Log.e("RxFirebaseSample", throwable.toString());
                });
    }
}
