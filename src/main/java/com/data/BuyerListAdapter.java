package com.data;

/**
 * Created by G1007 on 4/19/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greeno.marketplace.R;

import java.util.List;

public class BuyerListAdapter extends RecyclerView.Adapter<BuyerListAdapter.RecyclerViewHolders> {

    private List<BuyerData> itemList;
    private Context context;

    public BuyerListAdapter(Context context, List<BuyerData> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_row_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.category.setText(itemList.get(position).getCategory());
        holder.product.setText(itemList.get(position).getProduct());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView category;
        public TextView product;
        public TextView unit;
        public TextView quantity;
        public TextView countryName;
        public ImageView productImage;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            category = (TextView) itemView.findViewById(R.id.category_item);
            product = (TextView) itemView.findViewById(R.id.productName_item);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}