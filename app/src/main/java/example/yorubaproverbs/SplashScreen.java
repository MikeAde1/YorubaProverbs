package example.yorubaproverbs;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

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
                startActivity(new Intent(SplashScreen.this, Main2Activity.class));
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
