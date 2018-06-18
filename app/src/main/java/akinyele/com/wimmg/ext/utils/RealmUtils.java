package akinyele.com.wimmg.ext.utils;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.events.TrackedItemEvent;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.Const;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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
                () -> EventBus.getDefault().post(new TrackedItemEvent()),
                error -> Log.e(TAG, "saveTrackItem: ", error)
        );
    }

    public static ArrayList<TrackedItem> getTrackedItems() {
        mRealm = getRealmInstance();
        ArrayList<TrackedItem> items = new ArrayList<>();
        RealmResults<TrackedItem> results = mRealm.where(TrackedItem.class).findAll();

        results.sort("name");

        items.addAll(results);

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

    //==============================================================================================
    //         Helpers
    //==============================================================================================
    public static ArrayList<ArrayList<TrackedItem>> nameGoupedTrackedItems(Collection<TrackedItem> trackedItems) {

        ArrayList<ArrayList<TrackedItem>> groupedTrackedItems = new ArrayList<>();
        ArrayList<TrackedItem> group = new ArrayList<>();


        String tempName = "";
        for (TrackedItem item : trackedItems) {
            String itemName = item.getName().trim();

            boolean newGroup = ((tempName.isEmpty() || !itemName.equalsIgnoreCase(tempName)));
            if (newGroup) {
                groupedTrackedItems.add(group);
                group = new ArrayList<>();
            }
            group.add(item);
            tempName = itemName;

        }


        return groupedTrackedItems;
    }

    public static ArrayList<TrackedItem> getDayFilterTrackedItem(ArrayList<TrackedItem> trackedItems) {

        ArrayList<TrackedItem> filtered = new ArrayList<>();

        for (TrackedItem item : trackedItems) {

            String[] date = item.getDateBought().split("/");
            int dayOfMonth = Integer.valueOf(date[0]);
            int month = Integer.valueOf(date[1]);
            int year = Integer.valueOf(date[2]);

            Calendar dateBought = Utils.getCalandar(year, month, dayOfMonth);
            Calendar todady = Calendar.getInstance();

            boolean sameDay = (dateBought.get(Calendar.YEAR) == todady.get(Calendar.YEAR)
                    && dateBought.get(Calendar.DAY_OF_MONTH) == todady.get(Calendar.DAY_OF_MONTH)
                    && dateBought.get(Calendar.MONTH) == todady.get(Calendar.MONTH));
            if (sameDay) {
                filtered.add(item);
            }
        }

        return filtered;
    }

}
