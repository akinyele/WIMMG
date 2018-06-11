package ctrl_it.com.jm.wimmg.ext.utils;

import java.util.Collection;

import ctrl_it.com.jm.wimmg.app.models.RealmModels.TrackedItem;

public class CalcUtils {


    public static double getTrackedItemsTotal(Collection<TrackedItem> trackedItems) {
        double total = 0;

        for (TrackedItem item : trackedItems) {
            total = item.getCost() * item.getQuantity() + total;
        }

        return 0;
    }
}
