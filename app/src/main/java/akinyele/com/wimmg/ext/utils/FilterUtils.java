package akinyele.com.wimmg.ext.utils;

import java.util.ArrayList;
import java.util.Calendar;

import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;

public class FilterUtils {

    public static final int DAY_FILTER = 0;
    public static final int WEEK_FILTER = 1;
    public static final int MONTH_FILTER = 2;
    public static final int YEAR_FILTER = 3;


    public static ArrayList<TrackedItem> filterTrackItems(ArrayList<TrackedItem> trackedItems, int filter) {

        ArrayList<TrackedItem> filtered = new ArrayList<>();

        for (TrackedItem item : trackedItems) {

            String[] date = item.getDateBought().split("/");
            int dayOfMonth = Integer.valueOf(date[0]);
            int month = Integer.valueOf(date[1]);
            int year = Integer.valueOf(date[2]);

            Calendar dateBought = Utils.getCalender(year, month, dayOfMonth);
            Calendar today = Calendar.getInstance();

            boolean sameDay = (dateBought.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && dateBought.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
                    && dateBought.get(Calendar.MONTH) == today.get(Calendar.MONTH));

            switch (filter) {
                case DAY_FILTER:
                    if (sameDay) filtered.add(item);
                    break;
                case WEEK_FILTER:
                    if (Utils.isSameWeek(dateBought)) filtered.add(item);
                    break;
                case MONTH_FILTER:
                    if (Utils.isSameMonth(dateBought)) filtered.add(item);
                    break;
                case YEAR_FILTER:
                    if (Utils.isSameYear(dateBought)) filtered.add(item);
                    break;
            }

        }

        return filtered;
    }


}
