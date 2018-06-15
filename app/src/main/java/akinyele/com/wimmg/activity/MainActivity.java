package akinyele.com.wimmg.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import akinyele.com.wimmg.fragments.trackerFragment.TrackerFragment;
import akinyele.com.wimmg.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(android.R.id.content, new TrackerFragment())
                .commit();

    }

}
