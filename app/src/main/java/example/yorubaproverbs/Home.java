package example.yorubaproverbs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Home extends android.support.v4.app.Fragment {
    View view;
    ProgressBar progressBar;
    DatabaseReference dbref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference("users");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //Button button = view.findViewById(R.id.button);
        clickListeners();
        return view;
    }

    private void clickListeners() {
        CardView cardView = view.findViewById(R.id.cd);
        CardView cardView2 = view.findViewById(R.id.cd2);
        CardView cardView3 = view.findViewById(R.id.cd3);
        CardView cardView4 = view.findViewById(R.id.cd4);
        CardView cardView5 = view.findViewById(R.id.cd5);
        CardView cardView6 = view.findViewById(R.id.cd6);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name",getString(R.string.the_good_pesron)));
/*
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            if (String.valueOf(snapshot.child("Context").getValue()).contains("Good Person")){
                                Intent
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name","The Good Life"));
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name","Relationship"));
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name","Human Nature"));
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name","Rights and Responsibilities"));
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategorizedList.class).putExtra("name","Truism"));
            }
        });
    }

}