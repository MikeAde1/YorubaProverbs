package example.yorubaproverbs;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.yorubaproverbs.models.ProverbData;

public class ProverbDetails extends AppCompatActivity {
    private TextView content, translation, explanation, tvProv, tvTrans, tvExp;
    //private final String TEXT_KEY = "text_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proverb_details);

        content = findViewById(R.id.content);
        translation = findViewById(R.id.translation);
        explanation = findViewById(R.id.explanation);

        tvProv = findViewById(R.id.tvProverb);
        tvTrans = findViewById(R.id.tvTrans);
        tvExp = findViewById(R.id.tvExp);

        Typeface tp = Typeface.createFromAsset(ProverbDetails.this.getAssets(),"fonts/ProximaNovaRegular.ttf");
        tvProv.setTypeface(tp);
        tvTrans.setTypeface(tp);
        tvExp.setTypeface(tp);

        getMessage(getIntent());

    }

    public void getMessage(Intent intent){
        //store all in a session manager
        String proverb = intent.getStringExtra("value");
        String trans = intent.getStringExtra("translation");
        String exp = intent.getStringExtra("explanation");
//        Toast.makeText(ProverbDetails.this, get, Toast.LENGTH_SHORT).show();
        content.setText(proverb);
        translation.setText(trans);
        explanation.setText(exp);
        // check store manager to store data temporarily
    }
}
