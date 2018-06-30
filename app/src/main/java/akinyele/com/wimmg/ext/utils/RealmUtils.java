package akinyele.com.wimmg.ext.utils;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.Constants;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by akiny on 5/24/2018.
 */
public class RealmUtils {

    private static final String TAG = "RealmUtils";

    private static Realm mRealm;

    private static RealmConfiguration mConfiguration = Realm.getDefaultConfiguration();


    //==============================================================================================
    //          Tracked Items
    //==============================================================================================
    public static void saveTrackItem(TrackedItem item) {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        mRealm.executeTransactionAsync(
                realm -> realm.copyToRealmOrUpdate(item),
                () -> EventBus.getDefault().post(new TrackedItem()),
                error -> Log.e(TAG, "saveTrackItem: ", error)
        );
    }

    public static ArrayList<TrackedItem> getTrackedItems() {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        ArrayList<TrackedItem> items = new ArrayList<>();
        RealmResults<TrackedItem> results = mRealm.where(TrackedItem.class).findAll();

        results.sort("name");

        items.addAll(results);

        return items;
    }

    public static TrackedItem getTrackedItem(String name) {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        return mRealm.where(TrackedItem.class).equalTo("name", name).findFirst();
    }

    public static void removeTrackedItem(String id) {
        mRealm = Realm.getInstance(mConfiguration);
        mRealm.executeTransactionAsync(
                realm -> {
                    try {
                        realm.where(BudgetRealmModel.class).equalTo("id", id).findFirst();
                    } catch (NullPointerException ignored) {
                        Log.e(TAG, "removeBudgetItem: failed to remove item", ignored);
                    }
                }
        );

    }

    public static ArrayList<TrackedItem> getTrackedItemForCategory(String categoryName) {
        mRealm = Realm.getInstance(mConfiguration);

        return new ArrayList<>(mRealm.where(TrackedItem.class)
                .equalTo("category.name", categoryName)
                .findAll());
    }


    //==============================================================================================
    //          Categories
    //==============================================================================================
    public static Collection<CategoryRealmModel> getCategories() {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        return mRealm.where(CategoryRealmModel.class).findAll().sort("name");
    }

    public static void saveCategoryItem(CategoryRealmModel categoryRealmModel) {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());

        mRealm.executeTransactionAsync(realm -> {
            realm.copyToRealmOrUpdate(categoryRealmModel);
        });
    }

    //==============================================================================================
    //          Budget Item
    //==============================================================================================
    public static void saveBudgetItem(BudgetRealmModel budgetRealmModel) {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());

        mRealm.executeTransactionAsync(
                realm -> realm.copyToRealmOrUpdate(budgetRealmModel),
                () -> EventBus.getDefault().post(budgetRealmModel)
        );
    }

    public static void removeBudgetItem(String categoryName) {
        mRealm = Realm.getInstance(mConfiguration);

        mRealm.executeTransactionAsync(
                realm -> {
                    try {
                        realm.where(BudgetRealmModel.class).equalTo("category.name", categoryName).findFirst().deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Log.e(TAG, "removeBudgetItem: failed to remove item", ignored);
                        EventBus.getDefault().post(new BudgetRealmModel());
                    }

                }
        );
    }

    public static ArrayList<BudgetRealmModel> getBudgetItems() {
        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        return new ArrayList<>(mRealm.where(BudgetRealmModel.class).findAll());
    }


    public static BudgetRealmModel getBudgetItem(String categoryName) {
        mRealm = Realm.getInstance(mConfiguration);

        return mRealm.where(BudgetRealmModel.class).equalTo("category.name", categoryName)
                .findFirst();
    }

    //==============================================================================================
    //          Init
    //==============================================================================================
    public static void initCategoryData(Context context) {

        mRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        String[] categories = context.getResources().getStringArray(R.array.budget_categories);
        HashMap<String, Integer> colorHash = Constants.getCategoriesColorHashTable();
        HashMap<String, Integer> imageHash = Constants.getCategoriesImagesHashTable();

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

            boolean newGroup = ((!tempName.isEmpty() && !itemName.equalsIgnoreCase(tempName)));
            if (newGroup) {
                groupedTrackedItems.add(group);
                group = new ArrayList<>();
            }
            group.add(item);
            tempName = itemName;
        }

        if (!group.isEmpty()) groupedTrackedItems.add(group); // add the last group item;

        return groupedTrackedItems;
    }


    public static double getTotal(ArrayList<TrackedItem> trackedItems) {
        double total = 0;

        for (TrackedItem item : trackedItems) {
            total = total + item.getCost() * item.getQuantity();
        }
        return total;
    }


}
