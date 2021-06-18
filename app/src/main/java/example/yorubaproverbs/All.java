package example.yorubaproverbs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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


public class All extends Fragment {
    private static List<ProverbData> proverbDataList = new ArrayList<>();
    private ProverbListAdapter proverbListAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fb;
    private LinearLayout searching, mail;
    private DatabaseReference databaseRef;
    private final String TEXT_KEY = "text_key";
    private static int number;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("users");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_all,container,false);

        searching = rootview.findViewById(R.id.searching);
        mail = rootview.findViewById(R.id.mail);
        fb = rootview.findViewById(R.id.fab);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        setUpFloactingAction();
        setupAdapter();
        fetchData();
        fbClickListener();

        return rootview;
    }

    private void fbClickListener() {
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EmailActivity.class));
            }
        });
    }

    private void setUpFloactingAction() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fb.getVisibility() == View.VISIBLE){
                    fb.hide();
                }else if (dy < 0 && fb.getVisibility() != View.VISIBLE ){
                    fb.show();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupAdapter() {
        proverbDataList = new ArrayList<>();
        proverbListAdapter = new ProverbListAdapter(getContext(), proverbDataList, new ProverbListAdapter.Callback() {
            @Override
            public void itemClick(int position) {
                //Toast.makeText(getContext(), "Details coming soon", Toast.LENGTH_LONG).show();
                //this method overrides the onClick method in the adapter for the Viewholder
                //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getContext(), ProverbDetails.class).putExtra(TEXT_KEY,Integer.toString(position)));
                /*Intent intent = new Intent(getContext(), ProverbDetails.class);
                intent.putExtra("name", proverbDataList.get(position).getExplanation());
                intent.putExtra("translation", proverbDataList.get(position).getTranslation());
                startActivity(intent);*/
                number = position;
                assert getFragmentManager() != null;
                All.DialogFragment.newInstance().show(getFragmentManager(),DialogFragment.newInstance().getTag());
            }
        });
        recyclerView.setAdapter(proverbListAdapter);
    }

    private void fetchData() {

        databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        proverbDataList.clear();

                        //Toast.makeText(getContext(), String.valueOf(dataSnapshot),Toast.LENGTH_LONG).show();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            ProverbData proverbData = dataSnapshot1.getValue(ProverbData.class);
                            proverbDataList.add(proverbData);
                        }
                        Log.d("value" ,String.valueOf(proverbDataList.size()));
                        proverbListAdapter.setProverbDataList(proverbDataList);
                        proverbListAdapter.notifyDataSetChanged();

                        //Toast.makeText(getContext(), String.valueOf(proverbDataList),Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), (String.valueOf(databaseError)),Toast.LENGTH_SHORT).show();
                    }
                });
        searching.setVisibility(View.GONE);
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
