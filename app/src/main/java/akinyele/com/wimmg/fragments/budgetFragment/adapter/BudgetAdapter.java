package akinyele.com.wimmg.fragments.budgetFragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.activity.EditBudgetActivity;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.Constants;
import akinyele.com.wimmg.ext.utils.FilterUtils;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.ext.utils.ScreenUtils;
import akinyele.com.wimmg.ext.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<BudgetRealmModel> mData;
    private ArrayList<ArrayList<TrackedItem>> mTrackedItems;

    private ViewBinderHelper mViewBinderHelper = new ViewBinderHelper();

    private int filter = FilterUtils.DAY_FILTER;

    public BudgetAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        mTrackedItems = new ArrayList<>(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget_category_item, parent, false);
        return new BudgetViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BudgetRealmModel budgetItem = mData.get(position);

        if (holder instanceof BudgetViewHolder) {

            BudgetViewHolder mHolder = (BudgetViewHolder) holder;
            CategoryRealmModel categoryRealmModel = budgetItem.getCategory();
            String budgetAmount = Utils.decimalFormat(getBudgetAmount(budgetItem.getAmount(), filter), false);

            mHolder.mCategoryNameTextView.setText(budgetItem.getCategory().getName());
            mHolder.amountText.setText(budgetAmount);
            mHolder.mCategtoryView.setImageDrawable(mContext.getDrawable(categoryRealmModel.getImage()));
            mHolder.mCategoryColorView.setBackgroundTintList(mContext.getColorStateList(categoryRealmModel.getColor()));

            mViewBinderHelper.bind(mHolder.mSwipeRevealLayout, budgetItem.getCategory().getName());

            ArrayList<TrackedItem> items = mTrackedItems.get(position);

            double amount = RealmUtils.getTotal(items);

            String amountSpent = Utils.decimalFormat(amount, false);
            mHolder.mTransactionAmount.setText(items.size() + " transactions");
            mHolder.mAmountSpentTextView.setText(amountSpent);

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(0);
            String progressValue = df.format(amount / Double.valueOf(budgetAmount.replace(",", "")) * 100);

            int progress = Integer.valueOf(progressValue);
            mHolder.mProgressBar.setProgress(progress, true);


            mHolder.mDeleteImageView.setOnClickListener(
                    v -> {
                        mData.remove(position);

                        new MaterialDialog.Builder(mContext)
                                .title("Are you sure you want to delete this item ?")
                                .positiveText("yes")
                                .negativeText("cancel")
                                .autoDismiss(false)
                                .onPositive(
                                        (dialog, which) -> {
                                            RealmUtils.removeBudgetItem(budgetItem.getCategory().getName());
                                            notifyDataSetChanged();
                                            mHolder.mSwipeRevealLayout.close(true);
                                            dialog.dismiss();
                                        }
                                )
                                .onNegative(
                                        (dialog, which) -> dialog.dismiss()
                                ).show();

                    }
            );

            mHolder.mEditImageView.setOnClickListener(
                    v -> {
                        Intent editBudgetIntent = new Intent(mContext, EditBudgetActivity.class);
                        editBudgetIntent.putExtra(Constants.EXTRA_CATEOGORY_NAME, budgetItem.getCategory().getName());
                        mContext.startActivity(editBudgetIntent);
                    }
            );

        }
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        return size;
    }


    //==============================================================================================
    //              Helpers
    //==============================================================================================
    public void setData(ArrayList<BudgetRealmModel> budgetRealmModels) {
        mData = budgetRealmModels;
        dataSetChanged();
    }

    public void setFilter(int filter) {
        this.filter = filter;
        dataSetChanged();
    }

    public String getTotalBudget() {

        double total = 0;
        for (BudgetRealmModel item : mData) {
            total = item.getAmount() + total;
        }
        total = getBudgetAmount(total, filter);
        return Utils.decimalFormat(total, false);
    }

    public String getTransactionsAmount() {

        int mTransactionsAmount = 0;

        for (ArrayList<TrackedItem> trackedItems : mTrackedItems) {
            mTransactionsAmount = mTransactionsAmount + trackedItems.size();
        }

        return String.valueOf(mTransactionsAmount);
    }

    public String getBudgetSpent() {
        double mBudgetSpent = 0;

        for (ArrayList<TrackedItem> trackedItems : mTrackedItems) {
            for (TrackedItem item : trackedItems) {
                mBudgetSpent = item.getQuantity() * item.getCost() + mBudgetSpent;
            }
        }

        return Utils.decimalFormat(mBudgetSpent, false);
    }


    private double getBudgetAmount(double amount, int filter) {
        switch (filter) {
            case FilterUtils.WEEK_FILTER:
                return (amount * 7);
            case FilterUtils.MONTH_FILTER:
                return (amount * 28);
            case FilterUtils.YEAR_FILTER:
                return (amount * Calendar.getInstance().getWeeksInWeekYear() * 7);
            default:
                return amount;
        }
    }

    //==============================================================================================
    //          Boiler plates
    //==============================================================================================
    public void getTrackedItemsForBudgetCategories() {

        mTrackedItems = new ArrayList<>();

        for (BudgetRealmModel bugetItem : mData) {
            ArrayList<TrackedItem> items = RealmUtils.getTrackedItemForCategory(bugetItem.getCategory().getName());
            items = FilterUtils.filterTrackItems(items, filter);
            mTrackedItems.add(items);
        }
    }

    private void dataSetChanged() {
        getTrackedItemsForBudgetCategories();
        notifyDataSetChanged();
    }

    //==============================================================================================
    //              View Holder
    //==============================================================================================
    public class BudgetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_reveal_layout)
        SwipeRevealLayout mSwipeRevealLayout;
        @BindView(R.id.text_category_name)
        TextView mCategoryNameTextView;
        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;
        @BindView(R.id.frameLayout)
        View mCategoryColorView;
        @BindView(R.id.image_category)
        ImageView mCategtoryView;
        @BindView(R.id.text_transactions_amount)
        TextView mTransactionAmount;
        @BindView(R.id.text_transaction_amount)
        TextView amountText;
        @BindView(R.id.text_amount_spent)
        TextView mAmountSpentTextView;

        @BindView(R.id.ic_delete)
        ImageView mDeleteImageView;
        @BindView(R.id.ic_edit)
        ImageView mEditImageView;


        public BudgetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
