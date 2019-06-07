package com.qbent.enfinsapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qbent.enfinsapp.R;
import com.qbent.enfinsapp.model.CollectionPoint;

import java.util.List;

public class CollectionPointListRecyclerViewAdapter extends
    RecyclerView.Adapter<CollectionPointListRecyclerViewAdapter.CollectionPointListViewHolder> {

    private final List<CollectionPoint> collectionPoints;

    public CollectionPointListRecyclerViewAdapter(List<CollectionPoint> collectionPoints) {
        this.collectionPoints = collectionPoints;
    }

    @NonNull
    @Override
    public CollectionPointListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_collection_point, viewGroup, false);
        return new CollectionPointListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionPointListViewHolder collectionPointListViewHolder, int i) {
        collectionPointListViewHolder.mItem = collectionPoints.get(i);
        collectionPointListViewHolder.mNameView.setText(collectionPoints.get(i).getName());
        collectionPointListViewHolder.mAddressView.setText(collectionPoints.get(i).getMobileNo());
        collectionPointListViewHolder.mCollectionDayView.setText(collectionPoints.get(i).getCollectionDay());
    }

    @Override
    public int getItemCount() {
        return collectionPoints.size();
    }

    public class CollectionPointListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAddressView;
        public final TextView mCollectionDayView;
        public CollectionPoint mItem;

        public CollectionPointListViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.textName);
            mAddressView = (TextView) view.findViewById(R.id.textAddress);
            mCollectionDayView = (TextView) view.findViewById(R.id.textCollectionDay);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAddressView.getText() + "'";
        }
    }
}
