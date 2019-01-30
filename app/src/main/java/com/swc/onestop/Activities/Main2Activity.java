package com.swc.onestop.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.swc.onestop.Activities.Complaints.ComplaintActivity;
import com.swc.onestop.MainActivity_Models.CustomAdapter;
import com.swc.onestop.MainActivity_Models.Data_model;
import com.swc.onestop.R;
import com.swc.onestop.ui.ContactActivity;

import java.util.ArrayList;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {



    public static RecyclerView.Adapter adapter;

    public static RecyclerView recyclerView;
    public static ArrayList<Data_model> data1;
    //    static View.OnClickListener myOnClickListener;
//    private static ArrayList<Integer> removedItems;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static final int NUM_ITEMS = 2;
    private ViewPager mViewPager;
    int LIMIT = 2;

    static FirebaseFirestore db;
    static ArrayList<Map<String,Object>> feedList;

    DocumentSnapshot start;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_map:
                    startActivity( new Intent(Main2Activity.this, MapsActivity.class));
                    return true;
                case R.id.navigation_complaints:
                    startActivity(new Intent(Main2Activity.this,ComplaintActivity.class));
                    return true;
                case R.id.navigation_timing:
                    startActivity( new Intent(Main2Activity.this, Timings_activity.class));
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");


        if (ContextCompat.checkSelfPermission(Main2Activity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{Manifest.permission.INTERNET},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);


        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setViewPager(mViewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.outlook) {
            String packageName = "com.microsoft.office.outlook";
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) {
                // Bring user to the market or let them choose an app?
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            // Handle the camera action

        } else if (id == R.id.timetable) {

            startActivity(new Intent(this,Timetable.class));

        } else if (id == R.id.mess_menu) {

            startActivity(new Intent(this,Mess_Menu.class));

        } else if (id == R.id.internet_set) {

            startActivity(new Intent(this,Internet_settings.class));
        } else if (id == R.id.about) {
            startActivity(new Intent(this,AboutUs.class));
        } else if (id == R.id.contacts) {
            startActivity(new Intent(this,ContactActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FEED";
                case 1:
                    return "FAVOURITES";

            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View xyz = null;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                xyz = inflater.inflate(R.layout.feed, container, false);

                RecyclerView.LayoutManager layoutManager;
                recyclerView = (RecyclerView) xyz.findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                data1 = new ArrayList<Data_model>();
                db = FirebaseFirestore.getInstance();

                Log.d("test","ref"+db);

                feedList = new ArrayList<>();

//                more = (Button) findViewById(R.id.more);

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                final CollectionReference feedRef = rootRef.collection("feed");
                Query firstQuery = feedRef.orderBy("time", Query.Direction.ASCENDING);
                firstQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Map singleData = document.getData();

                                data1.add(new Data_model(
                                        singleData.get("title").toString(),
                                        singleData.get("subtitle").toString(),
                                        singleData.get("desc").toString(),
                                        "https://image.shutterstock.com/image-photo/studio-shot-young-man-looking-260nw-372072697.jpg",
                                        ""
                                ));

                                Log.d("test",singleData.get("title").toString());
//                                feedList.add(document.getData().);
                                //Toast.makeText(Main2Activity.this, document.getData().toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            Log.d("test","error");
                        }


                        adapter = new CustomAdapter(data1, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                });
//                for (int i = 0; i < MyData.titleArray.length; i++) {
//                    data.add(new Data_model(
//                            MyData.titleArray[i],
//                            MyData.subtitleArray[i],
//                            MyData.descArray[i],
//                            MyData.dpArray[i],
//                            MyData.imageArray[i]
//                    ));
//                }





            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                xyz = inflater.inflate(R.layout.fav, container, false);

                //favorites

            }



//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return xyz;
        }
    }





}
