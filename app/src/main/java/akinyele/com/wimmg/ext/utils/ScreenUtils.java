package akinyele.com.wimmg.ext.utils;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import akinyele.com.wimmg.R;

/**
 * Created by akiny on 5/24/2018.
 */
public class ScreenUtils {


    public static void changeStatusBarColor(Activity activity, int statusBarColor) {
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
    }


    public static ColorStateList getSimpleColorStateList(int[] colors) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };


        return new ColorStateList(states, colors);
    }
}
