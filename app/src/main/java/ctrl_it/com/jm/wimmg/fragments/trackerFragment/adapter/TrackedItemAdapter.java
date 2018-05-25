package ctrl_it.com.jm.wimmg.fragments.trackerFragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ctrl_it.com.jm.wimmg.R;
import ctrl_it.com.jm.wimmg.app.models.RealmModels.TrackedItem;

/**
 * Created by akiny on 5/24/2018.
 */
public class TrackedItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TrackedItem> mData;

    public TrackedItemAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracked_item, parent, false);
        return new TrackedItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        TrackedItem trackedItem = mData.get(position);
        if (holder instanceof TrackedItemViewHolder) {
            TrackedItemViewHolder trackedItemViewHolder = (TrackedItemViewHolder) holder;

            String category = trackedItem.getCategory();
            String name = trackedItem.getName();
            Double cost = trackedItem.getCost();

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //==============================================================================================
    //          Helpers
    //==============================================================================================
    public void setData(ArrayList<TrackedItem> trackedItems) {
        mData = trackedItems;
        notifyDataSetChanged();
    }


    //==============================================================================================
    //          View Holder
    //==============================================================================================
    public class TrackedItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView mNameText;

        @BindView(R.id.text_amount)
        TextView mAmount;

        @BindView(R.id.text_cost)
        TextView mCost;

        @BindView(R.id.image_category)
        ImageView mCategoryImage;

        public TrackedItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
