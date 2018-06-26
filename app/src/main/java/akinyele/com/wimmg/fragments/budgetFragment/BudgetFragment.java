package akinyele.com.wimmg.fragments.budgetFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.BudgetRealmModel;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import akinyele.com.wimmg.ext.utils.ScreenUtils;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        ButterKnife.bind(this, view);
        ScreenUtils.changeStatusBarColor(getActivity(), R.color.Gray);
        return view;
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
                .onPositive(
                        (dialog, which) -> {

                            BudgetRealmModel budgetRealmModel = new BudgetRealmModel();
                            budgetRealmModel.setAmount(createBudgetView.getAmount());
                            budgetRealmModel.setCategory(createBudgetView.getCategory());

                            RealmUtils.saveBudgetItem(budgetRealmModel);
                        }
                )
                .show();
    }


}
