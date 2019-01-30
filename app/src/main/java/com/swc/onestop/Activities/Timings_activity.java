package com.swc.onestop.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.swc.onestop.R;
import com.swc.onestop.Timings_activities.Ferry;
import com.swc.onestop.Timings_activities.IITG_Bus;
import com.swc.onestop.Timings_activities.Internal_bus;

public class Timings_activity extends AppCompatActivity {
    LinearLayout ferry,interalbus,iitgbus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.back_btn_color));
        toolbar.setTitle("Timings");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(Timings_activity.this,Main2Activity.class));
            }
        });

        ferry = (LinearLayout) findViewById(R.id.ferry_ll);
        interalbus = (LinearLayout) findViewById(R.id.internalbus_ll);
        iitgbus = (LinearLayout) findViewById(R.id.iitgbus_ll);

        

        ferry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ferry =new Intent(Timings_activity.this,Ferry.class);
                startActivity(ferry);
            }
        });


        interalbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent internaliitg =new Intent(Timings_activity.this,Internal_bus.class);
                startActivity(internaliitg);

            }
        });

        iitgbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iitgbus =new Intent(Timings_activity.this,IITG_Bus.class);
                startActivity(iitgbus);
            }
        });
    }



}
