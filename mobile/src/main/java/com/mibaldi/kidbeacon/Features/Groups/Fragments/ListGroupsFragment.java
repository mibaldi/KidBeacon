package com.mibaldi.kidbeacon.Features.Groups.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.mibaldi.kidbeacon.Data.Database.OwnGroupRealm;
import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Data.Models.User;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSingleActivity;
import com.mibaldi.kidbeacon.Features.Groups.Activities.ListGroupsActivity;
import com.mibaldi.kidbeacon.Features.Groups.Adapters.GroupsListAdapter;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import timber.log.Timber;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class ListGroupsFragment extends Fragment implements GroupsListAdapter.OnItemClickListener {
    @BindView(R.id.groupsList)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<OwnGroup> items = new ArrayList<>();
    ;
    private GroupsListAdapter adapter;

    public ListGroupsFragment() {
    }

    public static ListGroupsFragment newInstance() {
        ListGroupsFragment fragment = new ListGroupsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        items = new ArrayList<>();
        adapter = new GroupsListAdapter(items, ListGroupsFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getGroups();
        loadSwipeRefreshLayout();
    }

    @Override
    public void onItemClick(View view, OwnGroup ownGroup) {
        Intent intent = new Intent(getActivity(), GroupSingleActivity.class);
        intent.putExtra("ownGroup", ownGroup);
        startActivity(intent);
    }

    private void loadSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getGroups();
            }
        });
    }

    public void getGroups() {
        items.clear();
        AlertDialog dialog = new SpotsDialog(getActivity(),"Cargando grupos");
        dialog.show();
        RxFirebaseDatabase.observeValuesList(FirebaseManager.refUserGroups, String.class)
                .subscribe(userGroups -> {
                    for (String key : userGroups) {
                        RxFirebaseDatabase.observeSingleValue(FirebaseManager.refGroups.child(String.valueOf(key)), OwnGroup.class)
                                .subscribe(ownGroup1 -> {
                                    items.add(ownGroup1);
                                    dialog.hide();
                                    adapter.notifyDataSetChanged();
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }, throwable -> {
                                    Timber.e("ListGroupsFragment", throwable.toString());
                                });
                    }


                }, throwable -> {
                    Log.e("RxFirebaseSample", throwable.toString());
                });
    }
}
