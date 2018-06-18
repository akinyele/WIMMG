package akinyele.com.wimmg.fragments.trackerFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import akinyele.com.wimmg.app.events.TrackedItemEvent;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.utils.CalcUtils;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.ext.utils.Utils;
import akinyele.com.wimmg.fragments.BaseFragment;
import akinyele.com.wimmg.fragments.trackerFragment.views.AddItemDialogView;
import akinyele.com.wimmg.fragments.trackerFragment.views.DateSelectionView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import akinyele.com.wimmg.R;

import akinyele.com.wimmg.fragments.trackerFragment.adapter.TrackedItemAdapter;
import akinyele.com.wimmg.fragments.trackerFragment.views.SpendingInfoView;

/**
 * Created by akiny on 5/23/2018.
 */
public class TrackerFragment extends BaseFragment implements DateSelectionView.OnFilterSelectedListener {

    private static final String TAG = "TrackerFragment";

    @BindView(R.id.text_budget_amount)
    TextView mBudgetText;
    @BindView(R.id.text_total)
    TextView mTotalText;
    @BindView(R.id.text_transactions_amount)
    TextView mTransactionAmountText;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    TrackedItemAdapter itemAdapter;
    SpendingInfoView mSpendingInfoView;
    @BindView(R.id.view_date_filter_view)
    DateSelectionView mDateSelectionView;

    private String mTransactionAmount;
    private String mTotal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tracker, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mSpendingInfoView = new SpendingInfoView(getContext());
        setUpData();
    }

    //==============================================================================================
    //          Initialize
    //==============================================================================================
    public void setUpData() {
        ArrayList<TrackedItem> trackedItems = RealmUtils.getTrackedItems();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        itemAdapter = new TrackedItemAdapter(getContext());
        mRecyclerView.setAdapter(itemAdapter);
        setTrackedItemWithFilter(mDateSelectionView.getSelectedFilter());

        mTransactionAmount = String.valueOf(trackedItems.size());
        mTotal = String.valueOf(CalcUtils.getTrackedItemsTotal(trackedItems));

        mTotalText.setText(mTotal);
        mTransactionAmountText.setText(mTransactionAmount);

        mDateSelectionView.setOnFilterSelectedListener(this::onFilterSelected);

        setSpendingData();
    }


    //==============================================================================================
    //          Listeners
    //==============================================================================================
    @OnClick(R.id.fab_add_item)
    public void addTrackedItem() {


        AddItemDialogView mDialogView = new AddItemDialogView(getContext());

        new MaterialDialog.Builder(getContext())
                .title("Add Item")
                .negativeText("cancel")
                .positiveText("ok")
                .autoDismiss(false)
                .customView(mDialogView, true)
                .onPositive((dialog, which) -> {

                            String name = mDialogView.getName();
                            String cost = mDialogView.getCost();

                            if (cost.isEmpty()) {
                                showMessage("Please select a cost for the product.");
                                return;
                            }


                            if (name.isEmpty()) {
                                showMessage("Please select a name for this item.");
                                return;
                            }

                            TrackedItem trackedItem = new TrackedItem.Builder()
                                    .name(name)
                                    .cost(Double.valueOf(cost))
                                    .category(mDialogView.getCategory())
                                    .quantity(mDialogView.getAmount())
                                    .dateBought(mDialogView.getDateBought())
                                    .timeOfDay(mDialogView.getTimeOfDay())
                                    .build();

                            mDialogView.getCost();
                            RealmUtils.saveTrackItem(trackedItem);
                            dialog.dismiss();
                        }

                ).onNegative(
                (dialog, which) -> dialog.dismiss())
                .show();
    }


    //==============================================================================================
    //          Listeners
    //==============================================================================================
    @Subscribe
    public void TrackedItemEvent(TrackedItemEvent item) {
//        ArrayList<TrackedItem> trackedItems = RealmUtils.getTrackedItems();
//        itemAdapter.setData(trackedItems);

        onFilterSelected(mDateSelectionView.getSelectedFilter());

        setSpendingData();
    }

    //==============================================================================================
    //      Helpers
    //==============================================================================================
    private void setSpendingData() {
        double cost = CalcUtils.getTrackedItemsTotal(RealmUtils.getTrackedItems());
        mTotalText.setText(Utils.decimalFormat(cost));
    }

    private void setTrackedItemWithFilter(int filter) {
        switch (filter) {
            case DateSelectionView.DAY:
                ArrayList<TrackedItem> trackedItems = RealmUtils.getTrackedItems();
                itemAdapter.setData(RealmUtils.getDayFilterTrackedItem(trackedItems));
                break;
            case DateSelectionView.MONTH:
                break;
            case DateSelectionView.WEEK:
                break;
            case DateSelectionView.YEAR:
                break;
        }
    }


    @Override
    public void onFilterSelected(int selectedFiler) {
        setTrackedItemWithFilter(selectedFiler);
    }
}
