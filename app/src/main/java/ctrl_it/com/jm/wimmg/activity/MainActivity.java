package ctrl_it.com.jm.wimmg.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ctrl_it.com.jm.wimmg.R;
import ctrl_it.com.jm.wimmg.fragments.trackerFragment.TrackerFragment;

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
