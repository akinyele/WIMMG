package akinyele.com.wimmg.fragments.budgetFragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.ext.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<BudgetRealmModel> mData;

    public BudgetAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buget_category_item, parent, false);
        return new BudgetViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BudgetRealmModel budgetItem = mData.get(position);

        if (holder instanceof BudgetViewHolder) {

            BudgetViewHolder mHolder = (BudgetViewHolder) holder;
            CategoryRealmModel categoryRealmModel = budgetItem.getCategory();

            mHolder.mCategoryNameTextView.setText(budgetItem.getCategoryName());
            mHolder.amountText.setText(Utils.decimalFormat(budgetItem.getAmount()));
            mHolder.mCategtoryView.setImageDrawable(mContext.getDrawable(categoryRealmModel.getImage()));
            mHolder.mCategoryColorView.setBackgroundTintList(mContext.getColorStateList(categoryRealmModel.getColor()));


            ArrayList<TrackedItem> items = RealmUtils.getTrackedItemForCategory(budgetItem.getCategoryName());

            double amount = RealmUtils.getTotal(items);

            String amountSpent = Utils.decimalFormat(amount);
            mHolder.mTransactionAmount.setText(items.size() + " transactions");
            mHolder.mAmountSpentTextView.setText(amountSpent);

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(0);
            String progressValue = df.format(amount / budgetItem.getAmount() * 100);

            int progress = Integer.valueOf(progressValue);

            mHolder.mProgressBar.setProgress(progress, true);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    //==============================================================================================
    //              Helpers
    //==============================================================================================
    public void setData(ArrayList<BudgetRealmModel> budgetRealmModels) {
        mData = budgetRealmModels;
        notifyDataSetChanged();
    }


    //==============================================================================================
    //              View Holder
    //==============================================================================================
    public class BudgetViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.ic_info)
        ImageView mInfoImageView;

        public BudgetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
