package example.yorubaproverbs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import example.yorubaproverbs.adapter.ProverbListAdapter;
import example.yorubaproverbs.models.ProverbData;

public class CategorizedList extends AppCompatActivity  {

    private static int number;
    DatabaseReference dbref;

    private LinearLayout linearLayout;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ProverbListAdapter proverbListAdapter;

    private static List<ProverbData> proverbDataList = new ArrayList<>();
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_list);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference("users");

        assert getSupportActionBar() != null;

        /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_if_arrow_back_1063891);*/
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248,253,254)));
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setElevation(0);

        category = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">"+ category));

        progressBar = findViewById(R.id.pg);
        linearLayout = findViewById(R.id.waiting);

        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CategorizedList.this);
        recyclerView.setLayoutManager(mLayoutManager);

        setUpAdapter();
        fetchFromDb();
    }


    private void setUpAdapter() {
        proverbListAdapter = new ProverbListAdapter(CategorizedList.this, proverbDataList, new ProverbListAdapter.Callback() {
            @Override
            public void itemClick(int position) {
                //Toast.makeText(getContext(), "Details coming soon", Toast.LENGTH_LONG).show();
                //this method overrides the onClick method in the adapter for the Viewholder
                //Toast.makeText(CategorizedList.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getContext(), ProverbDetails.class).putExtra(TEXT_KEY,Integer.toString(position)));
                /*Intent intent = new Intent(CategorizedList.this, ProverbDetails.class);
                intent.putExtra("name", proverbDataList.get(position).getExplanation());
                intent.putExtra("translation", proverbDataList.get(position).getTranslation());
                startActivity(intent);*/
                number = position;
                DialogFragment.newInstance().show(getSupportFragmentManager(), DialogFragment.newInstance().getTag());

            }
        });
        recyclerView.setAdapter(proverbListAdapter);
    }

    private void fetchFromDb() {
        proverbDataList.clear();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (category) {
                    case "The Good Person":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }
                        break;
                    case "The Good Life":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("ìgbé ayé rere")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }
                        break;
                    case "Relationship":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("Ìbáṣepọ̀")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }

                        break;
                    case "Human Nature":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("Ìwà ẹ̀dá")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }
                        break;
                    case "Rights and Responsibilities":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("ẹ̀tọ́")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }
                        break;
                    case "Truism":
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //if (String.valueOf(snapshot.child("Context").getValue()).contains("ìwà rere")){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("òtítọ́")) {
                                ProverbData proverbData = snapshot.getValue(ProverbData.class);
                                proverbDataList.add(proverbData);
                            }
                            Log.d("value", String.valueOf(proverbDataList.size()));
                        }
                        break;
                }
                proverbListAdapter.setProverbDataList(proverbDataList);
                proverbListAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        linearLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static class DialogFragment extends BottomSheetDialogFragment{
        TextView proverb, meaning, explanation;

        public static DialogFragment newInstance(){
            return new DialogFragment();
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.bottom_sheet_layout, container,false);
            proverb = rootview.findViewById(R.id.proverb);
            meaning  = rootview.findViewById(R.id.meaning);
            explanation = rootview.findViewById(R.id.explanation);

            proverb.setText(proverbDataList.get(number).getContent());
            meaning.setText(proverbDataList.get(number).getTranslation());
            explanation.setText(proverbDataList.get(number).getExplanation());
            return rootview;
        }
    }


}
