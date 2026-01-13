package example.yorubaproverbs.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.yorubaproverbs.R;
import example.yorubaproverbs.models.ProverbData;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<ProverbData> proverbDataList = new ArrayList<>();
    private Callback callback;
    private Context context;

    public SearchAdapter(Context context, List<ProverbData> proverbData, Callback callback){
        this.context = context;
        this.proverbDataList = proverbData;
        this.callback = callback;
    }
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proverb_adapter,parent,false);
        return new SearchAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        ProverbData proverbData = proverbDataList.get(position);
        String content = proverbData.getContent();
        holder.textView.setText(content);
    }

    @Override
    public int getItemCount() {
        return proverbDataList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            callback.itemClick(getAdapterPosition());
        }

    }
    public interface Callback{
        //call an interface
        //declare the interface inside the needed class
        //implement the method within the interface into the needed function/method
        //add the declaration to the constructor of the class to be able to access it in another class
        void itemClick(int position);
    }

    public void setProverbDataList(List<ProverbData> proverbDataList) {
        this.proverbDataList = proverbDataList;
    }
}
