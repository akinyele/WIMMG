package akinyele.com.wimmg.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.TrackedItem;
import akinyele.com.wimmg.ext.utils.RealmUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<TrackedItem> mData;
    private Context mContext;

    public TransactionsAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TrackedItem trackedItem = mData.get(position);

        if (holder instanceof TransactionViewHolder) {

            TransactionViewHolder mHolder = (TransactionViewHolder) holder;

            mHolder.dateBoughtText.setText(trackedItem.getDateBought());
            mHolder.quatityText.setText(String.valueOf(trackedItem.getQuantity()));
            mHolder.transactionNameText.setText(trackedItem.getName());


            mHolder.deleteImageView.setOnClickListener(
                    v -> {
                        mData.remove(position);
                        RealmUtils.removeTrackedItem(trackedItem.getId());
                        notifyDataSetChanged();
                    }
            );

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<TrackedItem> data) {
        mData = data;
        notifyDataSetChanged();
    }


    //==============================================================================================
    //          View holder
    //==============================================================================================
    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_quantity)
        TextView quatityText;
        @BindView(R.id.text_name)
        TextView transactionNameText;
        @BindView(R.id.text_date)
        TextView dateBoughtText;
        @BindView(R.id.ic_delete)
        ImageView deleteImageView;


        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
