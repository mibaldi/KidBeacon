package com.mibaldi.kidbeacon.Features.Rastreator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.R;

import java.util.List;

public class ListBeaconsRastreatorAdapter extends RecyclerView.Adapter<ListBeaconsRastreatorAdapter.ListBeaconsRastreatorHolder>{

    private List<OwnBeacon> listItem;


    public ListBeaconsRastreatorAdapter(List<OwnBeacon> objects) {
        listItem = objects;

    }

    @Override
    public ListBeaconsRastreatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beaconlist_rastreator,parent,false);
        return new ListBeaconsRastreatorHolder(view);
    }

    @Override
    public void onBindViewHolder(ListBeaconsRastreatorHolder holder, int position) {
        holder.bindItem(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class ListBeaconsRastreatorHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Context context;

        public ListBeaconsRastreatorHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.groupName);
        }

        public void bindItem(final OwnBeacon ownBeacon) {
            name.setText(ownBeacon.name);
        }

    }
}
