package akinyele.com.wimmg.fragments.budgetFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.UltraViewPagerAdapter;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import java.util.Collection;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateBudgetDialog extends FrameLayout {


    @BindView(R.id.edit_text_budget_amount)
    EditText budgeAmountEditText;
    @BindView(R.id.range_spinner)
    Spinner rangeSpinner;
    @BindView(R.id.ultra_viewpager)
    UltraViewPager mCategoryUltraViewpager;

    private static final int DAY = 0;
    private static final int WEEK = 1;
    private static final int MONTH = 2;
    private static final int YEAR = 3;

    private String[] bugetRangePeriods = {"Day", "Week", "Month", "Year"};
    private CustomAdapter adapter;


    public CreateBudgetDialog(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.dialog_add_budget, this);
        ButterKnife.bind(this);
        init();
    }


    //==============================================================================================
    //              setup
    //==============================================================================================
    public void init() {

        gi

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, bugetRangePeriods);
        rangeSpinner.setAdapter(periodAdapter);
    }


    //==============================================================================================
    //          Getters
    //==============================================================================================
    public CategoryRealmModel getCategory() {

        return adapter.getCategory(mCategoryUltraViewpager.getCurrentItem()); //
    }

    public double getAmount() {

        Double amountToSpend = Double.valueOf(budgeAmountEditText.getText().toString());

        int period = rangeSpinner.getSelectedItemPosition();

        switch (period) {
            case WEEK:
                amountToSpend = amountToSpend / 7;
                break;
            case MONTH:
                amountToSpend = amountToSpend / 28;
                break;
            case YEAR:
                amountToSpend = amountToSpend / 365;
                break;
        }

        return amountToSpend;
    }


    //==============================================================================================
    //          Classes
    //==============================================================================================
    public class CustomAdapter extends PagerAdapter {

        Context mContext;

        ArrayList<CategoryRealmModel> mCategories;


        public CustomAdapter(Context context, Collection<CategoryRealmModel> mCategories) {
            this.mCategories = new ArrayList<>(mCategories);
            this.mContext = context;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            CategoryRealmModel categoryRealmModel = mCategories.get(position);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.item_category_item, container, false);

            TextView categoryTextView = view.findViewById(R.id.text_category);
            ImageView categoryImage = view.findViewById(R.id.image_category);

            categoryImage.setImageDrawable(getContext().getDrawable(categoryRealmModel.getImage()));
            categoryTextView.setText(categoryRealmModel.getName());

            container.addView(view);

            return container;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return true;
        }

        public CategoryRealmModel getCategory(int position) {

            return mCategories.get(position);
        }
    }
}

