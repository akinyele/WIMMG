package akinyele.com.wimmg;

import android.app.Application;

import akinyele.com.wimmg.ext.utils.RealmUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by akiny on 5/24/2018.
 */
public class WIMMG extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());

        dataSetup();
    }


    //==============================================================================================
    //          SETUP
    //==============================================================================================

    public void dataSetup() {

        boolean noCategories = (RealmUtils.getCategories().size() <= 0);
        if (noCategories) {
            RealmUtils.initCategoryData(this);
        }
    }

}
