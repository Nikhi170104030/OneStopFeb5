package com.swc.onestop.Activities.Complaints;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleComplaintActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Comment> myDataset;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails;
    private ComplaintCard card;
    private  String complainId,complainerName,complaintBody,complaintSubject,complaintSuppotCount;
    private  String complaintCommentCount,complaintDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_complaint);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(SingleComplaintActivity.this, ComplaintActivity.class));
            }
        });
        Intent i = getIntent();
        complainId = i.getStringExtra("complaintID");
        complainerName = i.getStringExtra("name");
        complaintBody = i.getStringExtra("body");
        complaintSubject = i.getStringExtra("sub");
        complaintSuppotCount = i.getStringExtra("supports");
        complaintCommentCount = i.getStringExtra("commentsCount");




        Log.i("shivam", "onCreate: "+complainId);
        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        /* retrieve all data */


        card = new ComplaintCard(complainerName, complaintDate, getDrawable(R.drawable.ic_launcher_foreground),
                complaintSubject, complaintBody,complainId, complaintSuppotCount, complaintCommentCount, false);

//        FirebaseFirestore.getInstance().collection("Complaint").document("Lohit").collection("complaint")
//                .document(complainId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("shivam", "DocumentSnapshot data: " + document.getData());
//                        card = new ComplaintCard(document.getData().get("name").toString(),document.getData().get("time_stamp").toString(),getDrawable(R.drawable.ic_launcher_foreground),
//                                document.getData().get("complain").toString(),document.getId(),"12","14",false);
//                    } else {
//                        Log.d("shivam", "No such document");
//                    }
//                } else {
//                    Log.d("shivam", "get failed with ", task.getException());
//                }
//            }
//        });
//        card = new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false);

        TextView x = findViewById(R.id.commentsInTotal);
        x.setText(card.getComments());

        x = findViewById(R.id.supporters);
        x.setText(card.getLikes());

        x = findViewById(R.id.nameOfComplainer);
        x.setText(card.getName());

        x = findViewById(R.id.dateOfComplaint);
        x.setText(card.getDate());

        x = findViewById(R.id.subTF);
        x.setText(card.getSubject());

        x = findViewById(R.id.msgTF);
        x.setText(card.getBody());

        final ImageView y = findViewById(R.id.youSupport);

        if (card.isSupported()) y.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
        else y.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));

        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card.isSupported()) {
                    //remove support
                    y.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    card.setSupported(false);
                } else {

                    //add support
                    y.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
                    card.setSupported(true);
                }

            }
        });


        mRecyclerView = findViewById(R.id.commentRecycler);
        myDataset = new ArrayList<>();

//        public void fetchcomplaints(){
            String hostel;

        hostel=userDetails.get("hostel");
        DocumentReference d;
        d=FirebaseFirestore.getInstance().collection("Complaint")
                .document(hostel).collection("complaint").document(complainId);
        d.collection("comment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("Shivam","in1");
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Log.i("Shivam","Comments"+documentSnapshot.getId() + "=> " + documentSnapshot.getData());
                        myDataset.add(new Comment(documentSnapshot.getData().get("name").toString(),documentSnapshot.getData().get("comment").toString()));
                    }

                } else {
                    Log.i("Shivam", "failed");
                }
            }
        });

        //live update but not working properly

//        d.collection("comment")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.i("Shivam", "Listen failed.", e);
//                            return;
//                        }
//
//
//                        for (QueryDocumentSnapshot doc : value) {
//                            Log.i("Shivam",doc.getId()+"->"+doc.getData().toString());
//                            myDataset.add(new Comment(doc.getData().get("name").toString(),doc.getData().get("comment").toString()));
//                        }
////                        Log.d(TAG, "Current cites in CA: " + cities);
//                    }
//                });
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new CommentAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);


        Button add = findViewById(R.id.addCommentButton);
        final EditText com = findViewById(R.id.addCommentET);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (com.getText().toString().isEmpty()) {
                    Toast.makeText(SingleComplaintActivity.this, "Empty Comment.", Toast.LENGTH_SHORT).show();
                } else {
                    Comment c = new Comment(userDetails.get("name"), com.getText().toString());

                    card.addComment(c);
                    myDataset.add(c);
                    mAdapter.notifyItemInserted(myDataset.size() - 1);
                    String comment;
                    comment = com.getText().toString();
                    addcomment(complainId, comment);


                    //add to database according to timestamp
                }


            }
        });
    }

    public void addcomment(String id, String commentText) {
        String hostel;
        Map<String, Object> commentDetail = new HashMap<>();
        String userid;


        userid = userDetails.get("id");
        String name = userDetails.get("name");
        hostel = userDetails.get("hostel");
        String comment;
        comment = commentText;
        commentDetail.put("user", userid);
        commentDetail.put("comment", comment);
        commentDetail.put("name", name);
        commentDetail.put("time_stamp", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance().collection("Complaint")
                .document(hostel).collection("complaint").document(id)
                .collection("comment").add(commentDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("Shivam", "commented");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Shivam", "error while commenting");
            }

        });


    }

//    public void supportcomplaint(String id){
//        String hostel;
//
//
//        final Map<String,Object> supportDetail = new HashMap<>();
//        String userid;
//
//
//        userid=userDetails.get("id");
//
//        hostel=userDetails.get("hostel");
//        supportDetail.put("user",userid);
//
//        final DocumentReference d;
//        d=FirebaseFirestore.getInstance().collection("Complaint")
//                .document(hostel).collection("complaint").document(id);
//        d.collection("support").whereEqualTo("user", userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    QuerySnapshot document = task.getResult();
//                    if (document.isEmpty()) {  //if not already supported
//
//                        d.collection("support").add(supportDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.i("Shivam","supported");
//                                Toast.makeText(complaints.this, "supported", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.i("Shivam","error while supporting");
//                            }
//                        });
//
//                    }
//                    else{
//                        //check support
//                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                            Log.i("Shivam","supports"+documentSnapshot.getId() + "=> " + documentSnapshot.getData());
//
//                        }
//                        Toast.makeText(complaints.this, "already supported", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.i("Shivam", "failed");
//                }
//            }
//        });
//
//
//    }
//    public void desupportcomplaint(String id){
//        String hostel;
//
//
//        String userid;
//
//        userid=userDetails.get("id");
////        name=userDetails.get("name");
//        hostel=userDetails.get("hostel");
//
////        supportDetail.put("user",userid);
//
//        final DocumentReference d;
//        d=FirebaseFirestore.getInstance().collection("Complaint")
//                .document(hostel).collection("complaint").document(id);
//        d.collection("support").whereEqualTo("user", userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    QuerySnapshot document = task.getResult();
//                    if (document.isEmpty()) {  //if not already supported
//                        Toast.makeText(complaints.this, "not already supported", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        //check support
//                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//
//                            Log.i("Shivam","supports"+documentSnapshot.getId() + "=> " + documentSnapshot.getData());
//                            String supportid=documentSnapshot.getId();
//                            d.collection("support").document(supportid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.i("Shivam", "Support deleted!");
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.i("Shivam", "Error deleting document"+ e);
//                                        }
//                                    });;
//                        }
//                        Toast.makeText(complaints.this, "desupported", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.i("Shivam", "failed");
//                }
//            }
//        });
//
//
//    }
}