package akinyele.com.wimmg.fragments.budgetFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.ext.utils.ScreenUtils;
import butterknife.ButterKnife;

public class BudgetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        ButterKnife.bind(this, view);
        ScreenUtils.changeStatusBarColor(getActivity(), R.color.Gray);
        return view;
    }
}
