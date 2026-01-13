package example.yorubaproverbs.presentation.splashscreen;

import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import dagger.hilt.android.AndroidEntryPoint;
import example.yorubaproverbs.presentation.dashboard.MainActivity;
import example.yorubaproverbs.R;

@AndroidEntryPoint
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        //ends the activity when on pause
    }
}
