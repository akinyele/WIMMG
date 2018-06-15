package akinyele.com.wimmg.ext.utils;

import java.util.Collection;

import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;


public class CalcUtils {


    public static double getTrackedItemsTotal(Collection<TrackedItem> trackedItems) {
        double total = 0;

        for (TrackedItem item : trackedItems) {
            total = (item.getCost() * item.getQuantity()) + total;
        }

        return total;
    }
}
