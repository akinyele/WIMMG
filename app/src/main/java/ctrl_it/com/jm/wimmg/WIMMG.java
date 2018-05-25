package ctrl_it.com.jm.wimmg;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by akiny on 5/24/2018.
 */
public class WIMMG extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

}
