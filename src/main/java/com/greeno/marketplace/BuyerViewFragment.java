package com.greeno.marketplace;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.data.BuyerData;
import com.data.BuyerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G1007 on 4/19/2016.
 */
public class BuyerViewFragment extends Fragment {
    private GridLayoutManager lLayout;

    public BuyerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buyer_list_view, container, false);

        List<BuyerData> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(getActivity(), 2);

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.list_of_items);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        BuyerListAdapter rcAdapter = new BuyerListAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /* *
                 Adapter Data
   */
    private List<BuyerData> getAllItemList() {

        List<BuyerData> allItems = new ArrayList<BuyerData>();
        allItems.add(new BuyerData("Need Apple", "100Kg ,Daily"));
        allItems.add(new BuyerData("Need Brinjal", "10tone ,Yearly"));
        allItems.add(new BuyerData("Need carrot", "500kg,Weekly"));
        allItems.add(new BuyerData("Need Coconut", "40ton,Monthly"));
        allItems.add(new BuyerData("Need Coconut", "40ton,Monthly"));
        allItems.add(new BuyerData("Need Coconut", "40ton,Monthly"));
        return allItems;
    }
}
