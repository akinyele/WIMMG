package ctrl_it.com.jm.wimmg.fragments.trackerFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ctrl_it.com.jm.wimmg.R;
import ctrl_it.com.jm.wimmg.app.events.TrackedItemEvent;
import ctrl_it.com.jm.wimmg.app.models.RealmModels.TrackedItem;
import ctrl_it.com.jm.wimmg.ext.utils.RealmUtils;
import ctrl_it.com.jm.wimmg.fragments.trackerFragment.adapter.TrackedItemAdapter;
import ctrl_it.com.jm.wimmg.fragments.trackerFragment.views.AddItemDialogView;

/**
 * Created by akiny on 5/23/2018.
 */
public class TrackerFragment extends Fragment {

    private static final String TAG = "TrackerFragment";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    TrackedItemAdapter itemAdapter;

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
        itemAdapter.setData(trackedItems);

    }


    //==============================================================================================
    //          Listeners
    //==============================================================================================


    @Subscribe
    public void TrackedItemEvent(TrackedItemEvent item) {
        ArrayList<TrackedItem> trackedItems = RealmUtils.getTrackedItems();
        itemAdapter.setData(trackedItems);
    }

    //==============================================================================================
    //      Helpers
    //==============================================================================================


}
