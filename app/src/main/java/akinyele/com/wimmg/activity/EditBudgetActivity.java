package akinyele.com.wimmg.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;

import java.util.ArrayList;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.Constants;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.CategoriesAdapter;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.TrackedItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBudgetActivity extends BaseActivity {
    private static final String TAG = "EditBudgetActivity";


    @BindView(R.id.edit_text_budget_amount)
    EditText mBudgetAmountEditText;
    @BindView(R.id.recycler_category)
    RecyclerView mCategoryRecycler;
    @BindView(R.id.recycler_transactions)
    RecyclerView mTransactionsRecycler;

    private String cateogryName;
    private BudgetRealmModel mBugetItem;
    private CategoriesAdapter categoriesAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_budget);
        ButterKnife.bind(this);

        initBundleVariable(getIntent().getExtras());
        init();

    }


    //==============================================================================================
    //          Setup
    //==============================================================================================
    public void initBundleVariable(Bundle bundle) {

        if (bundle == null) return;

        if (!bundle.containsKey(Constants.EXTRA_CATEOGORY_NAME)) {
            //todo make something went wrong material dialog to show to user and exit the class after
            return;
        }

        cateogryName = bundle.getString(Constants.EXTRA_CATEOGORY_NAME);
    }

    public void init() {

        mBugetItem = RealmUtils.getBudgetItem(cateogryName);
        if (mBugetItem == null) {
            //todo make something went wrong material dialog to show to user and exit the class after
            Log.i(TAG, "init: failed find budget item in database");
            return;
        }


        ArrayList<CategoryRealmModel> budgetCategories = new ArrayList<>(RealmUtils.getCategories());
        //todo remove the categories that have already been selected

        categoriesAdapter = new CategoriesAdapter(this);
        categoriesAdapter.setData(budgetCategories);
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mCategoryRecycler.setLayoutManager(carouselLayoutManager);
        mCategoryRecycler.setAdapter(categoriesAdapter);

        mBudgetAmountEditText.setText(String.valueOf(mBugetItem.getAmount()));


        TransactionsAdapter trackedItemAdapter = new TransactionsAdapter(this);
        trackedItemAdapter.setData(RealmUtils.getTrackedItemForCategory(mBugetItem.getCategory().getName()));

        mTransactionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mTransactionsRecycler.setAdapter(trackedItemAdapter);

    }


    //==============================================================================================
    //          Listeners
    //==============================================================================================
    @OnClick(R.id.btn_finish)
    public void finish() {

        String amount = mBudgetAmountEditText.getText().toString();
        if (amount.isEmpty()) {
            showMessage("Please enter a budget amount");
            return;
        }

        CategoryRealmModel categoryRealmModel = categoriesAdapter.getSelectedCategory();
        if (categoryRealmModel == null) {
            showMessage("Please select a category");
            return;
        }


        BudgetRealmModel budgetItem = new BudgetRealmModel();
        budgetItem.setAmount(Double.valueOf(amount));
        budgetItem.setCategory(categoryRealmModel);
        budgetItem.setId(mBugetItem.getId());

        RealmUtils.saveBudgetItem(budgetItem);
        finish();
    }


    @OnClick(R.id.text_cancel)
    public void cancel() {

    }

}
