package akinyele.com.wimmg.fragments.trackerFragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.Const;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CategoryRealmModel> mData;
    private String[] categoriesStrings;

    private int lastSelectedPos = -1;


    public CategoriesAdapter(Context context) {
        mContext = context;
        categoriesStrings = context.getResources().getStringArray(R.array.budget_categories);
        mData = new ArrayList<>(RealmUtils.getCategories());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_item, parent, false);
        return new CategoryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CategoryRealmModel category = mData.get(position);

        if (holder instanceof CategoryItemViewHolder) {
            CategoryItemViewHolder mHolder = (CategoryItemViewHolder) holder;

            boolean viewSelected = (lastSelectedPos == position);

            if (viewSelected) {
                mHolder.mContentView.setBackgroundResource(R.drawable.drawable_border);
            } else {
                mHolder.mContentView.setBackgroundResource(0);
            }

            mHolder.categoryText.setText(category.getName());
            mHolder.categoryImage.setImageDrawable(mContext.getDrawable(category.getImage()));
            mHolder.categoryImage.setBackgroundTintList(mContext.getColorStateList(category.getColor()));

        }

    }

    @Override
    public int getItemCount() {
        return categoriesStrings.length;
    }


    public CategoryRealmModel getSelectedCategory() {
        if (lastSelectedPos == -1)
            return null;

        CategoryRealmModel categoryRealmModel = new CategoryRealmModel();
        categoryRealmModel.setColor(mData.get(lastSelectedPos).getColor());
        categoryRealmModel.setImage(mData.get(lastSelectedPos).getImage());
        categoryRealmModel.setName(mData.get(lastSelectedPos).getName());

        return categoryRealmModel;
    }

    public void setData(ArrayList<CategoryRealmModel> categoryRealmModels) {
        mData = categoryRealmModels;
        notifyDataSetChanged();
    }


    //==============================================================================================
    //              View Holder
    //==============================================================================================
    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_content)
        View mContentView;
        //@BindView(R.id.view_category)
        //View categoryView;
        @BindView(R.id.image_category)
        ImageView categoryImage;
        @BindView(R.id.text_category)
        TextView categoryText;

        public CategoryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContentView.setOnClickListener(
                    v -> {
                        lastSelectedPos = getAdapterPosition();
                        notifyDataSetChanged();
                    }
            );
        }
    }
}
