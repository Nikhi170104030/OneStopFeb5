package com.swc.onestop.Activities.Complaints;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.swc.onestop.Activities.Internet_settings;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {

    private static final String TAG = "ComplaintActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ComplaintCard> myDataset;
    private  SessionManager sessionManager;
    private HashMap<String,String> userDetails;
    private ArrayList<String> supports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_complaint);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(ComplaintActivity.this,Main2Activity.class));
            }
        });
        Log.d(TAG, "onCreate: Starting.");
        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        fetchcomplaints();
//        Map<String,Object> ComplaintDetailsfetchcomplaints();
//        mRecyclerView = findViewById(R.id.complaintRecycler);

        // Set up the ViewPager with the sections adapter.

//        myDataset = new ArrayList<>();

//
//
//        myDataset.add(new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false));
//
//        myDataset.add(new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false));
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//
//        // specify an adapter (see also next example)
//        mAdapter = new ComplaintAdapter(myDataset, this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        FloatingActionButton fab = findViewById(R.id.complaintAdderFab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ComplaintActivity.this,AddComplaint.class));
//            }
//        });
        FloatingActionButton fab = findViewById(R.id.complaintAdderFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplaintActivity.this,AddComplaint.class));
            }
        });

        //gymkhana

    }

    public void fetchcomplaints(){
        String hostel;

        mRecyclerView = findViewById(R.id.complaintRecycler);

        // Set up the ViewPager with the sections adapter.

        myDataset = new ArrayList<>();


        hostel=userDetails.get("hostel");

        final Map<String,Object> complaintsDetail = new HashMap<>();

        FirebaseFirestore.getInstance().collection("Complaint").document(hostel).collection("complaint")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Log.i("Shivam",documentSnapshot.getId() + "=> " + documentSnapshot.getData().toString());
//                        ArrayList<String> new arr;
                         supports =  new ArrayList<>();
//                        supports = documentSnapshot.get("supports");

                        List<String> supports;
                        supports = (List<String>)documentSnapshot.getData().get("supports");
                        int supportCount;
                        supportCount= supports.size();
                        String c;
                        c= Integer.toString(supportCount);
                        Log.i("Shivam","Support"+supports);
                        boolean supported;
                        if(Arrays.asList(supports).contains(userDetails.get("name"))){
                            supported=true;
                        }else{
                            supported = false;
                        }
                        myDataset.add(new ComplaintCard(documentSnapshot.getData().get("name").toString(),documentSnapshot.getData().get("time_stamp").toString(),getDrawable(R.drawable.ic_launcher_foreground),
                                documentSnapshot.getData().get("complain").toString(),documentSnapshot.getData().get("body").toString(),documentSnapshot.getId(),c,"14",supported));
//                        documentSnapshot.getResult();

//                        List<String> supports = (List<String>) documentSnapshot.get("supports");
//                        Log.i("Shivam","hello"+supports.toString());

                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);


                        // specify an adapter (see also next example)
                        mAdapter = new ComplaintAdapter(myDataset, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                    }
                } else {
                    Log.i("Shivam", "failed");
                }
            }
        });


    }


}
