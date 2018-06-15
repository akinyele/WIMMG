package akinyele.com.wimmg.ext.utils;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.Const;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by akiny on 5/24/2018.
 */
public class RealmUtils {

    private static final String TAG = "RealmUtils";

    private static Realm mRealm;

    private static Realm getRealmInstance() {
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());
    }


    //==============================================================================================
    //          Tracked Items
    //==============================================================================================
    public static void saveTrackItem(TrackedItem item) {
        mRealm = getRealmInstance();
        mRealm.executeTransactionAsync(
                realm -> {
                    realm.copyToRealmOrUpdate(item);
                },
                () -> EventBus.getDefault().post(new akinyele.com.jm.wimmg.app.events.TrackedItemEvent()),
                error -> Log.e(TAG, "saveTrackItem: ", error)
        );
    }

    public static ArrayList<TrackedItem> getTrackedItems() {
        mRealm = getRealmInstance();
        ArrayList<TrackedItem> items = new ArrayList<>();
        items.addAll(mRealm.where(TrackedItem.class).findAll());

        return items;
    }

    public static TrackedItem getTrackedItem(String name) {
        mRealm = getRealmInstance();
        return mRealm.where(TrackedItem.class).equalTo("name", name).findFirst();
    }


    //==============================================================================================
    //          Categories
    //==============================================================================================
    public static Collection<CategoryRealmModel> getCategories() {
        mRealm = getRealmInstance();
        return mRealm.where(CategoryRealmModel.class).findAll();
    }


    //==============================================================================================
    //          Init
    //==============================================================================================
    public static void initCategoryData(Context context) {

        mRealm = getRealmInstance();
        String[] categories = context.getResources().getStringArray(R.array.budget_categories);
        HashMap<String, Integer> colorHash = Const.getCategoriesColorHashTable();
        HashMap<String, Integer> imageHash = Const.getCategoriesImagesHashTable();

        mRealm.executeTransaction(
                realm -> {
                    for (String category : categories) {
                        try {
                            CategoryRealmModel realmCategoryRealmModel = new CategoryRealmModel();
                            realmCategoryRealmModel.setName(category);
                            realmCategoryRealmModel.setColor(colorHash.get(category));
                            realmCategoryRealmModel.setImage(imageHash.get(category));

                            realm.copyToRealmOrUpdate(realmCategoryRealmModel);
                        } catch (NullPointerException e) {
                            Log.e(TAG, "initCategoryData: failed to create " + category + " realm object.", e);
                        }

                    }
                }
        );
    }

}
