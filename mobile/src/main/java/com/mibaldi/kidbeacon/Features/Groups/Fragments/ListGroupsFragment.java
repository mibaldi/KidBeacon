package com.mibaldi.kidbeacon.Features.Groups.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldi.kidbeacon.Data.Database.OwnGroupRealm;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Groups.Activities.GroupSingleActivity;
import com.mibaldi.kidbeacon.Features.Groups.Adapters.GroupsListAdapter;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class ListGroupsFragment extends Fragment  implements GroupsListAdapter.OnItemClickListener {
    @BindView(R.id.groupsList)
    RecyclerView recyclerView;

    private List<OwnGroup> items = new ArrayList<>();;

    public ListGroupsFragment() {
    }

    public static ListGroupsFragment newInstance( ) {
        ListGroupsFragment fragment = new ListGroupsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        OwnGroup ownGroup = new OwnGroup();
        ownGroup.name= "Grupo 1";
        ownGroup.creation_date="2";
        ownGroup.photo = "dafasf";

        items = new ArrayList<>();
        items.add(ownGroup);
        final GroupsListAdapter adapter = new GroupsListAdapter(items,ListGroupsFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(View view, OwnGroup ownGroup) {
        Intent intent = new Intent(getActivity(), GroupSingleActivity.class);
        intent.putExtra("ownGroup",ownGroup);
        startActivity(intent);
    }
}
