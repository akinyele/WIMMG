package akinyele.com.wimmg.fragments.trackerFragment.views;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;

import akinyele.com.wimmg.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateSelectionView extends FrameLayout {

    public static final int DAY = 0;
    public static final int WEEK = 1;
    public static final int MONTH = 2;
    public static final int YEAR = 3;

    @BindView(R.id.text_switcher_day)
    TextSwitcher mDayTextSwitcher;
    @BindView(R.id.text_switcher_week)
    TextSwitcher mWeekTextSwitcher;
    @BindView(R.id.text_switcher_month)
    TextSwitcher mMonthTextSwitcher;
    @BindView(R.id.text_switcher_year)
    TextSwitcher mYearTextSwitcher;

    private int selectedFilter = 11;

    public DateSelectionView(@NonNull Context context) {
        super(context);
        setUp();
    }

    public DateSelectionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    //==============================================================================================
    //              Setup
    //==============================================================================================
    public void setUp() {
        inflate(getContext(), R.layout.view_date_filter_view, this);
        ButterKnife.bind(this);
        setFilter(DAY);
    }


    //==============================================================================================
    //              Listeners
    //==============================================================================================
    @OnClick({R.id.text_switcher_week, R.id.text_switcher_day, R.id.text_switcher_year, R.id.text_switcher_month})
    public void setWeek(View view) {

        switch (view.getId()) {
            case R.id.text_switcher_day:
                setFilter(DAY);
                break;
            case R.id.text_switcher_week:
                setFilter(WEEK);
                break;
            case R.id.text_switcher_month:
                setFilter(MONTH);
                break;
            case R.id.text_switcher_year:
                setFilter(YEAR);
                break;
        }
    }

    //==============================================================================================
    //              Helpers
    //==============================================================================================
    public void deselectCurrentView() {

        switch (selectedFilter) {
            case DAY:
                mDayTextSwitcher.setText("Day");
                break;
            case WEEK:
                mWeekTextSwitcher.setText("Week");
                break;
            case MONTH:
                mMonthTextSwitcher.setText("Month");
                break;
            case YEAR:
                mYearTextSwitcher.setText("Year");
                break;
        }
    }

    public void setFilter(int filter) {
        switch (filter) {
            case DAY:
                mDayTextSwitcher.setText("Day");
                deselectCurrentView();
                selectedFilter = DAY;
                if (mOnFilterSelectedListener != null)
                    mOnFilterSelectedListener.onFilterSelected(filter);
                break;
            case WEEK:
                mWeekTextSwitcher.setText("Week");
                deselectCurrentView();
                selectedFilter = WEEK;
                if (mOnFilterSelectedListener != null)
                    mOnFilterSelectedListener.onFilterSelected(filter);
                break;
            case MONTH:
                mMonthTextSwitcher.setText("Month");
                deselectCurrentView();
                selectedFilter = MONTH;
                if (mOnFilterSelectedListener != null)
                    mOnFilterSelectedListener.onFilterSelected(filter);
                break;
            case YEAR:
                mYearTextSwitcher.setText("Year");
                deselectCurrentView();
                selectedFilter = YEAR;
                if (mOnFilterSelectedListener != null)
                    mOnFilterSelectedListener.onFilterSelected(filter);
                break;
        }
    }

    //==============================================================================================
    //              Getters and Setters
    //==============================================================================================
    public int getSelectedFilter() {
        return selectedFilter;
    }

    public void setTextColor(@ColorRes int color) {
        //mDayTextSwitcher
    }

    //==============================================================================================
    //              Interface
    //==============================================================================================
    private OnFilterSelectedListener mOnFilterSelectedListener;

    public interface OnFilterSelectedListener {
        void onFilterSelected(int selectedFiler);
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener onFilterSelectedListener) {
        mOnFilterSelectedListener = onFilterSelectedListener;
    }

}
