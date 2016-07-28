package com.mibaldi.kidbeacon.Features.Beacons.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import java.util.List;

public class ListBeaconsAdapter extends RecyclerView.Adapter<ListBeaconsAdapter.ListBeaconsHolder>{

    public interface OnItemClickListener {
        void onItemClick(View view, OwnBeacon item);
    }
    private List<OwnBeacon> listItem;
    private final OnItemClickListener listener;

    public ListBeaconsAdapter(List<OwnBeacon> objects, OnItemClickListener listener) {
        listItem = objects;
        this.listener = listener;
    }

    @Override
    public ListBeaconsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beaconlist,parent,false);
        return new ListBeaconsHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ListBeaconsHolder holder, int position) {
        holder.bindItem(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ListBeaconsHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Context context;
        public ListBeaconsAdapter.OnItemClickListener listener;
        public ListBeaconsHolder(View itemView, ListBeaconsAdapter.OnItemClickListener listener) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.groupName);
            this.listener = listener;
        }

        public void bindItem(final OwnBeacon ownBeacon) {
            name.setText(ownBeacon.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(itemView, ownBeacon);
                }
            });
        }

    }
}
