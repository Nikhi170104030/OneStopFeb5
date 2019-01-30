package com.swc.onestop.Activities.Complaints;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {
    private List<ComplaintCard> mDataset;
    private Context context;
    private HashMap<String,String> userDetails;
    private SessionManager sessionManager;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private TextView name,date,sub,body, id,supports,comments;
        private ImageView mySupport,personImage;
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameOfComplainer);
            date = v.findViewById(R.id.dateOfComplaint);
            personImage = v.findViewById(R.id.imageOfComplainer);
            mySupport = v.findViewById(R.id.youSupport);
            sub = v.findViewById(R.id.subTF);
            body = v.findViewById(R.id.msgTF);
            id=v.findViewById(R.id.id);


//            Log.i("shivam","abcde"+id);
            supports = v.findViewById(R.id.supporters);
            comments = v.findViewById(R.id.commentsInTotal);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    final String;
                    Intent i = new Intent(context,SingleComplaintActivity.class);
                    Log.i("shivam",((TextView)view.findViewById(R.id.id)).getText().toString());
                    String id=((TextView)view.findViewById(R.id.id)).getText().toString();
                    String name = ((TextView)view.findViewById(R.id.nameOfComplainer)).getText().toString();
                    String body = ((TextView)view.findViewById(R.id.msgTF)).getText().toString();
                    String sub = ((TextView)view.findViewById(R.id.subTF)).getText().toString();
                    String supports = ((TextView)view.findViewById(R.id.supporters)).getText().toString();
                    String comments = ((TextView)view.findViewById(R.id.commentsInTotal)).getText().toString();
                    String date = ((TextView)view.findViewById(R.id.dateOfComplaint)).getText().toString();
                    ImageView support = ((ImageView)view.findViewById(R.id.youSupport));
                    i.putExtra("complaintID",id);
                    i.putExtra("name",name);
                    i.putExtra("body",body);
                    i.putExtra("subject",sub);
                    i.putExtra("date",date);
                    i.putExtra("supports",supports);
                    i.putExtra("commentsCount",comments);


//                    i.putExtra("mySupport",support);
//                    i.putExtra

//                    Log.i("shivam",id);
                    context.startActivity(i);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplaintAdapter(List<ComplaintCard> myDataset , Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ComplaintAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new vie
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.complaint_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(mDataset.get(position).getName());
        holder.date.setText(mDataset.get(position).getDate());
        holder.sub.setText(mDataset.get(position).getSubject());
        holder.body.setText(mDataset.get(position).getBody());
        holder.id.setText(mDataset.get(position).getId());
        holder.id.setVisibility(View.INVISIBLE);
        holder.supports.setText(mDataset.get(position).getLikes());
        holder.comments.setText(mDataset.get(position).getComments());
        holder.personImage.setImageDrawable(mDataset.get(position).getImage());
        if(mDataset.get(position).isSupported()){
            holder.mySupport.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_black_24dp));
        }
        else{
            holder.mySupport.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_border_black_24dp));
        }

        holder.mySupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDataset.get(position).isSupported()){
                    //remove support

                    holder.mySupport.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_border_black_24dp));

                    mDataset.get(position).setSupported(false);
                }
                else{
                    //support
                    holder.mySupport.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_black_24dp));
                    mDataset.get(position).setSupported(true);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

