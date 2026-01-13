package example.yorubaproverbs;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Backend extends Application {
 FirebaseDatabase firebaseDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
    }
}
