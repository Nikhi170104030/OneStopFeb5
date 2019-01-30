package com.swc.onestop.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.swc.onestop.R;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {
    String feedbackurl ="https://goo.gl/forms/jk1nzkzHSXbBoa542";
    String reportbugurl ="https://goo.gl/forms/BLPlCF1FlTRonoXU2";
    String tusharfb ="https://www.facebook.com/ydv.tushar";
    String nikhilfb ="https://www.facebook.com/nikhil.gaddam.5";
    String ashwinfb ="https://www.facebook.com/ashwin.kulkarni.90";
    String ankurfb ="https://www.facebook.com/ankur.ingale.35";
    String anshumanfb ="https://www.facebook.com/anshuman.dhar.9";
    String shivamfb ="https://www.facebook.com/shivamkr286";
    String vivekfb ="https://www.facebook.com/SuperVivekRaj";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About Us");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(AboutUs.this,Main2Activity.class));
            }
        });

        final LinearLayout feedback = (LinearLayout) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(feedbackurl));
                startActivity(i);
            }
        });
        final LinearLayout reportbug = (LinearLayout) findViewById(R.id.reportbug);
        reportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(reportbugurl));
                startActivity(i);
            }
        });

        final LinearLayout tushar = (LinearLayout) findViewById(R.id.tushar);
        tushar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tusharfb));
                startActivity(i);
            }
        });
        final LinearLayout nikhil = (LinearLayout) findViewById(R.id.nikhil);
        nikhil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(nikhilfb));
                startActivity(i);
            }
        });
        final LinearLayout ashwin = (LinearLayout) findViewById(R.id.ashwin);
        ashwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ashwinfb));
                startActivity(i);
            }
        });
        final LinearLayout ankur = (LinearLayout) findViewById(R.id.ankur);
        ankur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ankurfb));
                startActivity(i);
            }
        });
        final LinearLayout anshuman = (LinearLayout) findViewById(R.id.anshunam);
        anshuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(anshumanfb));
                startActivity(i);
            }
        });
        final LinearLayout shivam = (LinearLayout) findViewById(R.id.shivam);
        shivam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(shivamfb));
                startActivity(i);
            }
        });
        final LinearLayout vivek = (LinearLayout) findViewById(R.id.vivek);
        vivek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(vivekfb));
                startActivity(i);
            }
        });





    }
    public void onClick(View v){

    }

}
