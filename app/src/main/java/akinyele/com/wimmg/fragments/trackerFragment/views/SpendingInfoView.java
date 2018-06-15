package akinyele.com.wimmg.fragments.trackerFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.utils.CalcUtils;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import akinyele.com.wimmg.R;

public class SpendingInfoView extends FrameLayout {

    @BindView(R.id.text_budget_amount)
    TextView mBudgetText;
    @BindView(R.id.text_total)
    TextView mTotalText;
    @BindView(R.id.text_transactions_amount)
    TextView mTransactionAmountText;


    private String mTransactionAmount;
    private String mTotal;
    private String budget;


    public SpendingInfoView(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.view_spending_info, this);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        setupViews();
    }


    //==============================================================================================
    //              SetUp
    //==============================================================================================
    private void setupViews() {

        ArrayList<TrackedItem> trackedItems = RealmUtils.getTrackedItems();
        mTransactionAmount = String.valueOf(trackedItems.size());
        mTotal = String.valueOf(CalcUtils.getTrackedItemsTotal(trackedItems));

        mTotalText.setText(mTotal);
        mTransactionAmountText.setText(mTransactionAmount);

    }


    //==============================================================================================
    //              Listeners
    //==============================================================================================


    //==============================================================================================
    //              Events
    //==============================================================================================
    @Subscribe
    public void trasactionEvent(TrackedItem trackedItem) {

    }


}
