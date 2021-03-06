
package com.swc.onestop.TabFragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.swc.onestop.EachFragmentMenu.FragmentMenu;
import com.swc.onestop.EachFragmentMenu.FragmentMenuAdapter;
import com.swc.onestop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<FragmentMenu> myDataset;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        myDataset = new ArrayList<>();
        myDataset.add(new FragmentMenu(getResources().getDrawable(R.drawable.ic_launcher_foreground) ,
                "E-Rickshaw" ,
                "Fast In-campus transport","Transport"));

        myDataset.add(new FragmentMenu(getResources().getDrawable(R.drawable.ic_launcher_foreground) ,
                "Taxi" ,
                "Booking-Based Taxi Services","Transport"));

        mRecyclerView = view.findViewById(R.id.tab2recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FragmentMenuAdapter(myDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}