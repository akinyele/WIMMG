package akinyele.com.wimmg.fragments.budgetFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.ext.utils.ScreenUtils;
import akinyele.com.wimmg.fragments.budgetFragment.adapter.BudgetAdapter;
import akinyele.com.wimmg.fragments.budgetFragment.views.CreateBudgetDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BudgetFragment extends Fragment {


    @BindView(R.id.fab_add_budget)
    FloatingActionButton addBudgetFab;
    @BindView(R.id.text_amount_spent)
    TextView mBudgetAmountSpentTextView;
    @BindView(R.id.text_amount_total)
    TextView mTotalBudgetTextView;
    @BindView(R.id.text_transactions_amount)
    TextView mTransactionAmountTextView;
    @BindView(R.id.text_budget_title)
    TextView mTextBudgetTitle;
    @BindView(R.id.recycler_view_budget)
    RecyclerView mBudgetRecyclerView;

    private BudgetAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        ButterKnife.bind(this, view);
        ScreenUtils.changeStatusBarColor(getActivity(), R.color.Gray);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        init();
    }

    //==============================================================================================
    //              Setup
    //==============================================================================================
    public void init() {

        adapter = new BudgetAdapter(getContext());
        mBudgetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBudgetRecyclerView.setAdapter(adapter);

        adapter.setData(RealmUtils.getBugetItems());

    }

    //==============================================================================================
    //              Listeners
    //==============================================================================================
    @OnClick(R.id.fab_add_budget)
    public void addBudgetItem() {

        CreateBudgetDialog createBudgetView = new CreateBudgetDialog(getContext());

        MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                .title("Create your budget")
                .positiveText("add budget")
                .negativeText("cancel")
                .customView(createBudgetView, true)
                .autoDismiss(false)
                .onNegative(
                        (dialog, which) -> dialog.dismiss()
                )
                .onPositive(
                        (dialog, which) -> {

                            Double budgetAmount = createBudgetView.getAmount();
                            CategoryRealmModel categoryRealmModel = createBudgetView.getCategory();

                            if (budgetAmount == null) {
                                Toast.makeText(getContext(), "Please enter a budget amount.", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            if (categoryRealmModel == null) {
                                Toast.makeText(getContext(), "Please select a budget category.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            BudgetRealmModel budgetRealmModel = new BudgetRealmModel();
                            budgetRealmModel.setAmount(budgetAmount);
                            budgetRealmModel.setCategory(categoryRealmModel);
                            budgetRealmModel.setCategoryName(categoryRealmModel.getName());
                            RealmUtils.saveBudgetItem(budgetRealmModel);

                            dialog.dismiss();
                        }
                )
                .show();

    }


    //==============================================================================================
    //         Event
    //==============================================================================================
    @Subscribe
    public void budgetEvent(BudgetRealmModel budgetRealmModel) {
        adapter.setData(RealmUtils.getBugetItems());
    }

    @Subscribe
    public void trackedItem(TrackedItem trackedItem) {
        adapter.notifyDataSetChanged();
    }
}
