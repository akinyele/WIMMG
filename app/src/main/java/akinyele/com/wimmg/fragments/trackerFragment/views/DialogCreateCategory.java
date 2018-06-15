package akinyele.com.wimmg.fragments.trackerFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.CategoriesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogCreateCategory extends FrameLayout {


    @BindView(R.id.recycler_view_category)
    RecyclerView mRecyclerView;

    CategoriesAdapter categoriesAdapter;

    public DialogCreateCategory(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.dialog_add_category, this);
        ButterKnife.bind(this);
        init();
    }

    //==============================================================================================
    //              SETUP
    //==============================================================================================
    public void init() {

        categoriesAdapter = new CategoriesAdapter(getContext());
        mRecyclerView.setAdapter(categoriesAdapter);
        mRecyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false)
        );
    }


    //==============================================================================================
    //              Helpers
    //==============================================================================================
    public CategoryRealmModel getCategory() {
        return categoriesAdapter.getSelectedCategory();
    }

}
