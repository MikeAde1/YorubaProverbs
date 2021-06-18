package example.yorubaproverbs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    private EditText name, proverb, explanation, translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        name = findViewById(R.id.fname);
        proverb = findViewById(R.id.edProverb);
        explanation = findViewById(R.id.edExplanation);
        translation = findViewById(R.id.edTranslation);
    }

    public void send(View view) {
        String get_name = name.getText().toString();
        String get_proverb =  proverb.getText().toString();
        String get_explanation = explanation.getText().toString();
        String get_translation = translation.getText().toString();
        if (get_name.equals("") || get_proverb.equals("") || get_explanation.equals("") || get_translation.equals("")){
            Snackbar.make(view, "Please fill in all of the entries", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
        else {
            Intent it = new Intent(Intent.ACTION_SEND);
            it.putExtra(Intent.EXTRA_EMAIL, new String[]{"michael.adeneye@yahoo.com"});
            it.putExtra(Intent.EXTRA_SUBJECT,"Addition to Proverbs catalogue");
            it.putExtra(Intent.EXTRA_TEXT, "Proverb: " +get_proverb + "\n" + "Translation: " + get_translation + "\n"
            + "Explanation: "+get_explanation);
            it.setType("message/rfc822");
            try {
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            } catch (ActivityNotFoundException e) {
                //TODO: Handle case where no email app is available
            }
            startActivity(Intent.createChooser(it,"Choose Mail App"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
