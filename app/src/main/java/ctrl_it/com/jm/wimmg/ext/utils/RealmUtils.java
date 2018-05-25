package ctrl_it.com.jm.wimmg.ext.utils;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ctrl_it.com.jm.wimmg.app.events.TrackedItemEvent;
import ctrl_it.com.jm.wimmg.app.models.RealmModels.TrackedItem;
import io.realm.Realm;

/**
 * Created by akiny on 5/24/2018.
 */
public class RealmUtils {

    private static final String TAG = "RealmUtils";

    private static Realm mRealm;


    //==============================================================================================
    //          Tracked Items
    //==============================================================================================
    public static void saveTrackItem(TrackedItem item) {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(
                realm -> realm.copyToRealmOrUpdate(item),
                () -> EventBus.getDefault().post(new TrackedItemEvent()),
                error -> Log.e(TAG, "saveTrackItem: ", error)
        );
    }

    public static ArrayList<TrackedItem> getTrackedItems() {
        mRealm = Realm.getDefaultInstance();
        ArrayList<TrackedItem> items = new ArrayList<>();
        items.addAll(mRealm.where(TrackedItem.class).findAll());

        return items;
    }

    public static TrackedItem getTrackedItem(String name) {
        mRealm = Realm.getDefaultInstance();
        return mRealm.where(TrackedItem.class).equalTo("name", name).findFirst();
    }


}
