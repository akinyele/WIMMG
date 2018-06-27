package akinyele.com.wimmg.fragments.budgetFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.CategoriesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateBudgetDialog extends FrameLayout {


    @BindView(R.id.edit_text_budget_amount)
    EditText budgeAmountEditText;
    @BindView(R.id.range_spinner)
    Spinner rangeSpinner;
    @BindView(R.id.recycler_category)
    RecyclerView mCategoryRecycler;

    private static final int DAY = 0;
    private static final int WEEK = 1;
    private static final int MONTH = 2;
    private static final int YEAR = 3;

    private String[] bugetRangePeriods = {"Day", "Week", "Month", "Year"};
    private CategoriesAdapter mCategoriesAdapter;


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

        mCategoriesAdapter = new CategoriesAdapter(getContext());
        mCategoryRecycler.setAdapter(mCategoriesAdapter);

        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mCategoryRecycler.setLayoutManager(carouselLayoutManager);
        mCategoryRecycler.addOnScrollListener(new CenterScrollListener());

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, bugetRangePeriods);
        rangeSpinner.setAdapter(periodAdapter);
    }


    //==============================================================================================
    //          Getters
    //==============================================================================================
    public CategoryRealmModel getCategory() {
        return mCategoriesAdapter.getSelectedCategory();
    }

    public Double getAmount() {

        String doubleString = budgeAmountEditText.getText().toString();

        if (doubleString.isEmpty())
            return null;

        Double amountToSpend = Double.valueOf(doubleString);

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

}

