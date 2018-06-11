package ctrl_it.com.jm.wimmg.fragments.trackerFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ctrl_it.com.jm.wimmg.R;
import ctrl_it.com.jm.wimmg.app.models.RealmModels.TrackedItem;
import ctrl_it.com.jm.wimmg.ext.utils.CalcUtils;
import ctrl_it.com.jm.wimmg.ext.utils.RealmUtils;
import ctrl_it.com.jm.wimmg.ext.utils.Utils;

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
    @OnClick(R.id.fab_add_item)
    public void addTrackedItem() {


        AddItemDialogView mDialogView = new AddItemDialogView(getContext());

        new MaterialDialog.Builder(getContext())
                .title("Add Item")
                .negativeText("cancel")
                .positiveText("ok")
                .customView(mDialogView, true)
                .onPositive((dialog, which) -> {
                            TrackedItem trackedItem = new TrackedItem.Builder()
                                    .name(mDialogView.getName())
                                    .cost(Double.valueOf(mDialogView.getCost()))
                                    .category(mDialogView.getCategory())
                                    .dateBought(mDialogView.getDateBought())
                                    .timeOfDay(mDialogView.getTimeOfDay())
                                    .build();
                            mDialogView.getCost();
                            RealmUtils.saveTrackItem(trackedItem);
                        }
                ).show();
    }


    //==============================================================================================
    //              Events
    //==============================================================================================
    @Subscribe
    public void trasactionEvent(TrackedItem trackedItem) {

    }


}
