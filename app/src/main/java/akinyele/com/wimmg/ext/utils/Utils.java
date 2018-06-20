package akinyele.com.wimmg.ext.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import akinyele.com.wimmg.ext.Const;

/**
 * Created by akiny on 5/23/2018.
 */
public class Utils {


    public static String decimalFormat(Double cost) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setCurrency(Currency.getInstance(Locale.getDefault()));

        boolean numberIsIrrational = (cost % 1 > 0);
        if (numberIsIrrational)
            df.setMinimumFractionDigits(2);

        return df.format(cost);
    }


    //==============================================================================================
    //              Date Utils
    //==============================================================================================
    public static String getDateString(int year, int month, int dayOfMonth) {
        String monthString = Const.month[month];
        return monthString + " " + dayOfMonth + ", " + year;
    }

    public static Calendar getCalandar(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar;
    }

    /**
     * @param c1
     * @return
     */
    public static boolean isSameWeek(Calendar c1) {
        Calendar today = Calendar.getInstance();

        int date1 = c1.get(Calendar.DAY_OF_MONTH);
        int date2 = today.get(Calendar.DAY_OF_MONTH);


        int dayOfWeekToday = today.get(Calendar.DAY_OF_WEEK);
        int daysLeftInWeek = (7 - dayOfWeekToday);


        boolean isLastWeek = !(date1 > (date2 - dayOfWeekToday));
        boolean isFollowingWeek = (date1 > (date2 + daysLeftInWeek));

        return !(isLastWeek || isFollowingWeek);
    }

    public static boolean isSameMonth(Calendar c1) {
        Calendar c2 = Calendar.getInstance();

        return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }

    public static boolean isSameYear(Calendar c1) {
        Calendar c2 = Calendar.getInstance();

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }
}

