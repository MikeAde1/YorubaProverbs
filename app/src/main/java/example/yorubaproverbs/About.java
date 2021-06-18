package example.yorubaproverbs;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class About extends Fragment {
    private TextView tv, tv_feedback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_about, container, false);

        tv_feedback = rootview.findViewById(R.id.tv_feedback);
        tv = rootview.findViewById(R.id.tv_title);
        Typeface tp = Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(),"fonts/NotoSans-Bold.ttf");
        tv.setTypeface(tp);

        String email = getResources().getString(R.string.feedback);
        tv_feedback.setText(email);
        final SpannableString ss = new SpannableString(email);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
            }
        };
        ss.setSpan(clickableSpan,49, email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Linkify.addLinks(tv_feedback, Linkify.EMAIL_ADDRESSES);

        return rootview;
    }
    }
