package example.yorubaproverbs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import example.yorubaproverbs.adapter.ProverbListAdapter;
import example.yorubaproverbs.models.ProverbData;

public class Main2Activity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener{

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MaterialSearchView materialSearchView;
    private Spinner spinner;
    private DatabaseReference databaseRef;
    private RecyclerView recyclerView;

    private List<ProverbData> dataList = new ArrayList<>();
    private ProverbListAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("users");

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout =  findViewById(R.id.tabs);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        CardView cardView = findViewById(R.id.cardview);
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Main2Activity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.GONE);
        setupAdapter();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        spinner = findViewById(R.id.spinner);

        materialSearchView = findViewById(R.id.searchView);
        materialSearchView.closeSearch();
        materialSearchView.setOnQueryTextListener(this);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //cardView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        recyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onSearchViewClosed() {
                //cardView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                dataList.clear();
                spinner.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                R.layout.spinner_background);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        if (!checkInternetConnection()){
            Toast.makeText(Main2Activity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                return true;
            }
            else {
                return false;
            }
    }

    private void setupAdapter() {
        dataList = new ArrayList<>();
         searchAdapter = new ProverbListAdapter(Main2Activity.this, dataList, new ProverbListAdapter.Callback() {
            @Override
            public void itemClick(int position) {
            //Toast.makeText(Main2Activity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, ProverbDetails.class);
                intent.putExtra("value", dataList.get(position).getContent());
                intent.putExtra("translation",dataList.get(position).getTranslation());
                intent.putExtra("explanation", dataList.get(position).getExplanation());
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(searchAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        //MenuItemCompat.setOnActionExpandListener(sea)
        MenuItem menuItem = menu.findItem(R.id.action_search);//search button
        materialSearchView.setMenuItem(menuItem);
        if (!materialSearchView.isSearchOpen()){
            spinner.setVisibility(View.GONE);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //final String option = spinner.getSelectedItem().toString();
        return false;
    }
    /**let the model class implement parceable
     * add arraylist to a bundle object (bundle.putParceableArraylist()
     * fragment.setArgument(bundle)
     * return fragment
     * and the fragment is called in the parent activity
     * get back data using getIntent.getExtras.getParceableArrayList(key)
     * for (ProverbData proverbData: proverbDataList){
     if (proverbData.getContext().contains("typed value")){
     Newarraylist.add(proverbData);
     }
     //when you want to search for more information from the proverb while listening to clicks, do
     Newarraylist.get(getAdapterposition).getContent
     Newarraylist.get(getAdapterposition).getTranslation
     Newarraylist.get(getAdapterposition).getExplanation
     }**/

    @Override
    public boolean onQueryTextChange(final String newText) {
        final String option = spinner.getSelectedItem().toString();
        if (newText.length() > 25){
            spinner.setVisibility(View.GONE);
        }
        else{
            spinner.setVisibility(View.VISIBLE);
        }
        if(!newText.isEmpty()) {

                if (option.equals("English")){

                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String context = String.valueOf(snapshot.child("Translation").getValue());
                                //might use ignore case in this scenario
                                if (context.toLowerCase().contains(newText.toLowerCase())) {
                                    recyclerView.setVisibility(View.VISIBLE);

                                    ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                    dataList.add(proverbData);

                                }
                                Log.d("value", String.valueOf(dataList.size()));

                            }
                            searchAdapter.setProverbDataList(dataList);
                            searchAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else if (option.equals("Yorùbá")) {
                        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataList.clear();

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String context = String.valueOf(snapshot.child("Content").getValue());
                                        //might use ignore case in this scenario
                                        if (context.toLowerCase().contains(newText.toLowerCase())) {
                                            recyclerView.setVisibility(View.VISIBLE);

                                            ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                            dataList.add(proverbData);

                                        }

                                        Log.d("value", String.valueOf(dataList.size()));

                                    }
                                    searchAdapter.setProverbDataList(dataList);
                                    searchAdapter.notifyDataSetChanged();
                                }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                }
        }
        else {
            dataList.clear();
            recyclerView.setVisibility(View.GONE);
        }

        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
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
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            assert getArguments() != null;
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    return new Home();
                case 1:
                    return new All();
                case 2:
                    return new About();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
            spinner.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
        }
        else{
            super.onBackPressed();
        }
    }
}
