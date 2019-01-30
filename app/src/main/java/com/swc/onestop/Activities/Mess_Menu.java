package com.swc.onestop.Activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.swc.onestop.MyDownloadManager;
import com.swc.onestop.R;


import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Mess_Menu extends AppCompatActivity implements OnLoadCompleteListener,OnPageChangeListener {


    String dir;
    private  String menuMonth;
    PDFView pdfview;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String url = "https://nitt.edu/home/students/facilitiesnservices/hostelsnmess/messes/Mess_Menu.pdf";

    private DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess__menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(Mess_Menu.this,Main2Activity.class));
            }
        });
        checkmonth();
//        editor.putString("shivam","kumar");
//        editor.commit();

//        sharedPreferences.getString("shivam","extra string if not shivam");
        dir =Environment.getExternalStorageDirectory().toString()+"/OneStop/";
//        FirebaseFirestore.getInstance().collection("messmenu").document("updatemenu").addSnapshotListener(new EventListener<QuerySnapshot>() {


        final NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("BARAK", "BRAHMAPUTRA", "DHANSIRI", "DIBANG", "DIHING","KAMENG","KAPILI","LOHIT", "MANAS", "SIANG", "SUBHANSIRI","UMIAM"));
        niceSpinner.attachDataSource(dataset);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        //url="one";
                        LoadPDF("BARAK",url);
                        break;
                    case 1:
                        //url="two";
                        LoadPDF("BRAHMAPUTRA",url);
                        break;
                    case 2:
                       // url="three";
                        LoadPDF("DHANSIRI",url);
                        break;
                    case 3:
                       // url="one";
                        LoadPDF("DIBANG",url);
                        break;
                    case 4:
                       // url="two";
                        LoadPDF("DIHING",url);
                        break;
                    case 5:
                        //url="three";
                        LoadPDF("KAMENG",url);
                        break;
                    case 6:
                       // url="one";
                        LoadPDF("KAPILI",url);
                        break;
                    case 7:
                       // url="two";
                        LoadPDF("LOHIT",url);
                        break;
                    case 8:
                       // url="three";
                        LoadPDF("MANAS",url);
                        break;
                    case 9:
                       // url="one";
                        LoadPDF("SIANG",url);
                        break;
                    case 10:
                        //url="two";
                        LoadPDF("SUBHANSIRI",url);
                        break;
                    case 11:
                       // url="three";
                        LoadPDF("UMIAM",url);
                        break;
                        
                    default:
                      //  url="no match";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pdfview = (PDFView) findViewById(R.id.pdf);

                






    }

    public void MessUpdated(){

        File del = new File(dir);
        if (del.isDirectory())
        {
            String[] children = del.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(del, children[i]).delete();
            }
        }
    }
    public void updateSharedPreference(){

        sharedPreferences = getApplicationContext().getSharedPreferences("Shivam", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("month",menuMonth);

        editor.commit();
        MessUpdated();

//        String sp = sharedPreferences.getString("month","Not Available");
//        Log.i("shivam", "checkmonth: "+sp);
    }
    public void checkmonth(){
        sharedPreferences = getApplicationContext().getSharedPreferences("Shivam", 0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString("month","January");
//        editor.commit();
//
        final String sp = sharedPreferences.getString("month","Not Available");
        Log.i("shivam", "checkmonth: "+sp);

        FirebaseFirestore.getInstance().collection("messmenu").document("updatemenu").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.i("shivam", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.i("shivam", "Current data: " + snapshot.getData().get("month").toString());
                    menuMonth = snapshot.getData().get("month").toString();
                    if (menuMonth.equals(sp)){
                        Log.i("shivam","its up to date");
                    }
                    else{
                        Log.i("shivam","its time to delete");
                        updateSharedPreference();
                    }
                } else {
                    Log.i("shivam", "Current data: null");
                }
            }
        });





    }
    public void LoadPDF(String Hostel,String url){
        if(isExternalStorageReadable() && isExternalStorageWritable()){

            File file = new File(dir, "/"+Hostel+".pdf");

            Log.i("shivam",dir+"/"+ Hostel + ".pdf");
            Log.i("Testing",getFilesDir().toString());
            if (!file.exists()){
                Log.i("shivam","File Does not exists");
                beginDownload(Hostel,url);

            }
            else {
                SetPDF(Hostel);
                Log.i("shivam","File exists");

            }





        }
    }


//    .onSnapshot(function(snapshot) {
//        snapshot.docChanges().forEach(function(change) {
//            if (change.type === "added") {
//                console.log("New city: ", change.doc.data());
//            }
//            if (change.type === "modified") {
//                console.log("Modified city: ", change.doc.data());
//            }
//            if (change.type === "removed") {
//                console.log("Removed city: ", change.doc.data());
//            }
//        });
//    });

    private void beginDownload(final String Hostel, String URL) {


        BroadcastReceiver listener = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                //String data = intent.getStringExtra("Download complete");
                    Log.i("shivam",Hostel+" File Downloading at"+dir);
                  SetPDF(Hostel);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(listener,
                new IntentFilter("Download complete"));

        MyDownloadManager downloadManager = new MyDownloadManager();

        downloadManager.Download(getApplicationContext(),Hostel, URL);





    }
    public void SetPDF(String Hostel){
        Log.e("test","PDF Loaded");
        File file = new File(dir+"/"+Hostel + ".pdf");
        pdfview.fromFile(file)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onLoad(Mess_Menu.this)
                .onPageChange(Mess_Menu.this)
                .load();

    }



    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    @Override
    public void loadComplete(int nbPages) {
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }


}




