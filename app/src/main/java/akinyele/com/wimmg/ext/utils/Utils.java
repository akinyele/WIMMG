package akinyele.com.wimmg.ext.utils;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

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
}
