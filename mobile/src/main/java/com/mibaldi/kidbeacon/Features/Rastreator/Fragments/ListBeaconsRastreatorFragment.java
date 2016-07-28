package com.mibaldi.kidbeacon.Features.Rastreator.Fragments;

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
import com.mibaldi.kidbeacon.Features.Rastreator.Adapters.ListBeaconsRastreatorAdapter;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class ListBeaconsRastreatorFragment extends Fragment {
    @BindView(R.id.beaconsList  )
    RecyclerView recyclerView;

    private List<OwnBeacon> items = new ArrayList<>();;
    private OwnGroup ownGroup;

    public ListBeaconsRastreatorFragment() {
    }

    public static ListBeaconsRastreatorFragment newInstance(OwnGroup ownGroup ) {
        ListBeaconsRastreatorFragment fragment = new ListBeaconsRastreatorFragment();
        Bundle args = new Bundle();
        args.putParcelable("ownGroup", ownGroup);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beacon_rastreator_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            ownGroup = getArguments().getParcelable("ownGroup");
        }
        OwnBeacon ownBeacon = new OwnBeacon();
        ownBeacon.name= "Beacon 1";
        ownBeacon.uuid = UUID.randomUUID().toString();
        ownBeacon.major = "1";
        ownBeacon.minor = "0";

        items = new ArrayList<>();
        items.add(ownBeacon);
        final ListBeaconsRastreatorAdapter adapter = new ListBeaconsRastreatorAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
