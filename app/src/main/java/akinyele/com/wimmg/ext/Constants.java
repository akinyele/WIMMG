package akinyele.com.wimmg.ext;

import java.util.HashMap;
import java.util.LinkedHashMap;

import akinyele.com.wimmg.R;


/**
 * Created by akiny on 5/23/2018.
 */
public class Constants {

    public static final String PREF_TAX = "tax";


    public static final String EXTRA_CATEOGORY_NAME = "category_name";


    public static final String month[] = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };

    public static HashMap<String, Integer> getCategoriesImagesHashTable() {
        LinkedHashMap<String, Integer> categoriesHash = new LinkedHashMap<>();
        categoriesHash.put("Education", R.drawable.ic_education);
        categoriesHash.put("Personal Care", R.drawable.ic_personal_care);
        categoriesHash.put("Entertainment", R.drawable.ic_entertainment);
        categoriesHash.put("Food", R.drawable.ic_food);
        categoriesHash.put("Giving", R.drawable.ic_giving);
        categoriesHash.put("Housing", R.drawable.ic_housing);
        categoriesHash.put("Insurance", R.drawable.ic_insurance);
        categoriesHash.put("Medical", R.drawable.ic_medical);
        categoriesHash.put("Transportation", R.drawable.ic_transportation);
        categoriesHash.put("Saving", R.drawable.ic_saving);
        categoriesHash.put("Utilities", R.drawable.ic_utilities);
        return categoriesHash;
    }

    public static LinkedHashMap<String, Integer> getCategoriesColorHashTable() {
        LinkedHashMap<String, Integer> categoriesHash = new LinkedHashMap<>();
        categoriesHash.put("Education", R.color.SandyBrown);
        categoriesHash.put("Personal Care", R.color.HotPink);
        categoriesHash.put("Entertainment", R.color.Gainsboro);
        categoriesHash.put("Food", R.color.GreenYellow);
        categoriesHash.put("Giving", R.color.Gold);
        categoriesHash.put("Housing", R.color.Chocolate);
        categoriesHash.put("Insurance", R.color.DodgerBlue);
        categoriesHash.put("Medical", R.color.MediumVioletRed);
        categoriesHash.put("Transportation", R.color.DarkSlateGray);
        categoriesHash.put("Saving", R.color.DarkGreen);
        categoriesHash.put("Utilities", R.color.DimGray);
        return categoriesHash;
    }

}
