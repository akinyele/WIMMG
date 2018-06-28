package akinyele.com.wimmg.fragments.trackerFragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.utils.RealmUtils;

public class CustomSpinnerAdapter extends BaseAdapter {


    private Context mContext;
    private List<CategoryRealmModel> mCategoryRealmModels;

    public CustomSpinnerAdapter(Context context) {
        mContext = context;
        mCategoryRealmModels = new ArrayList<>(RealmUtils.getCategories());
    }

    @Override
    public int getCount() {
        return mCategoryRealmModels.size();
    }

    @Override
    public Object getItem(int i) {
        CategoryRealmModel categoryRealmModel = new CategoryRealmModel();
        categoryRealmModel.setColor(mCategoryRealmModels.get(i).getColor());
        categoryRealmModel.setImage(mCategoryRealmModels.get(i).getImage());
        categoryRealmModel.setName(mCategoryRealmModels.get(i).getName());
        return categoryRealmModel;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CategoryRealmModel categoryRealmModel = mCategoryRealmModels.get(i);

        @SuppressLint("ViewHolder")
        View spinnerView = LayoutInflater.from(mContext).inflate(R.layout.spinner_category_item, null);

        View colorStripView = spinnerView.findViewById(R.id.view_color_strip);
        TextView categoryText = spinnerView.findViewById(R.id.text_category);

        colorStripView.setBackgroundColor(mContext.getResources().getColor(categoryRealmModel.getColor()));
        categoryText.setText(categoryRealmModel.getName());

        return spinnerView;
    }
}
