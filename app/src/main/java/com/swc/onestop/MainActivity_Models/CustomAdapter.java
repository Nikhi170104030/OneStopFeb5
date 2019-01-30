package com.swc.onestop.MainActivity_Models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swc.onestop.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Data_model> dataSet;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewtitle;
        TextView textViewSubtitle;
        TextView textViewinfo;
        ImageView imageViewdesc;
        ImageView imageViewdp;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewdp = (ImageView) itemView.findViewById(R.id.dp);
            this.textViewtitle = (TextView) itemView.findViewById(R.id.title_name);
            this.textViewSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.textViewinfo = (TextView) itemView.findViewById(R.id.info);
            this.imageViewdesc = (ImageView)itemView.findViewById(R.id.image_desc);


        }
    }

    public CustomAdapter(ArrayList<Data_model> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);



        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        TextView textViewtitle = holder.textViewtitle;
        TextView textViewSubtitle = holder.textViewSubtitle;
        TextView textViewinfo = holder.textViewinfo;
        ImageView imageViewdesc = holder.imageViewdesc;
        ImageView imageViewdp= holder.imageViewdp;


        textViewtitle.setText(dataSet.get(listPosition).getTitle());
        textViewSubtitle.setText(dataSet.get(listPosition).getSubtitle());
        textViewinfo.setText(dataSet.get(listPosition).getDesc());

        String dpurl = dataSet.get(listPosition).getdp();
        String imageurl = dataSet.get(listPosition).getImage();

        Log.d("url", String.valueOf(dpurl.isEmpty()));

       if(!dpurl.isEmpty())
       {
           Picasso.get().load(dpurl).into(imageViewdp);
       }
       if(!imageurl.isEmpty())
       {
           Picasso.get().load(imageurl).into(imageViewdesc);
       }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}