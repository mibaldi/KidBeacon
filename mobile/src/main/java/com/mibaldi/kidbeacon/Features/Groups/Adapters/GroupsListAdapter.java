package com.mibaldi.kidbeacon.Features.Groups.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import java.util.List;

public class GroupsListAdapter extends RecyclerView.Adapter<GroupsListAdapter.GroupsListHolder>{

    public interface OnItemClickListener {
        void onItemClick(View view, OwnGroup item);
    }
    private List<OwnGroup> listItem;
    private final OnItemClickListener listener;

    public GroupsListAdapter(List<OwnGroup> objects, OnItemClickListener listener) {
        listItem = objects;
        this.listener = listener;
    }

    @Override
    public GroupsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouplist,parent,false);
        return new GroupsListHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(GroupsListHolder holder, int position) {
        holder.bindItem(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class GroupsListHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Context context;
        public GroupsListAdapter.OnItemClickListener listener;
        public GroupsListHolder(View itemView, GroupsListAdapter.OnItemClickListener listener) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.groupName);
            this.listener = listener;
        }

        public void bindItem(final OwnGroup ownGroup) {
            name.setText(ownGroup.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(itemView, ownGroup);
                }
            });
        }

    }
}
