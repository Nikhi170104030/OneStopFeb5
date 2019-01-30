package com.swc.onestop.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.swc.onestop.R;

import java.util.ArrayList;
import java.util.List;

public class AllDetailActivity extends Activity {

    private static final String TAG = "Tab1Fragment";
    private Spinner sItems;
    private TextView mainTitle;
    private TextView sideTitle1;
    private TextView sideTitle2;
    private TextView sideTitle3;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String whichFrag = i.getStringExtra("whichFrag");
        int  p = i.getIntExtra("position",0);
        this.setFinishOnTouchOutside(true);

        if(whichFrag.charAt(0)=='G') {
            switch (p) {
                case 0:
                    setContentView(R.layout.activity_all_detail);
                    break;
                case 1:
                    setContentView(R.layout.activity_all_detail);
                    break;
                case 2:
                    setContentView(R.layout.activity_all_detail_spinner);
                    List<String> spinnerArray =  new ArrayList<String>();
                    spinnerArray.add("item1");
                    spinnerArray.add("item2");

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, spinnerArray);

                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    Spinner sItems = (Spinner) findViewById(R.id.spinner);
                    sItems.setAdapter(adapter);
                    break;
                case 3:
                    setContentView(R.layout.activity_all_detail_spinner);
                    break;
                    default: ;
            }
        }
        if(whichFrag.charAt(0)=='T')
            setContentView(R.layout.activity_all_detail);

    }
}
