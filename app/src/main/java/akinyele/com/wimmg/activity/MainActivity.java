package akinyele.com.wimmg.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import java.util.ArrayList;

import akinyele.com.wimmg.ext.CustomObjects.NonSwipeableViewPager;
import akinyele.com.wimmg.fragments.budgetFragment.BudgetFragment;
import akinyele.com.wimmg.fragments.trackerFragment.TrackerFragment;
import akinyele.com.wimmg.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.view_pager)
    NonSwipeableViewPager mNonSwipeableViewPager;
    @BindView(R.id.ah_bottom_nav)
    AHBottomNavigation mBottomNavigation;

    PagerAdapter mAdapter;
    ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .replace(android.R.id.content, new TrackerFragment())
//                .commit();

        setup();
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {

        switch (position) {
            case 0:
                mNonSwipeableViewPager.setCurrentItem(position);
                return true;
            case 1:
                mNonSwipeableViewPager.setCurrentItem(position);
                return true;
        }
        return false;
    }

    public void setup() {
        mFragments.add(new TrackerFragment());
        mFragments.add(new BudgetFragment());

        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);

        mNonSwipeableViewPager.setAdapter(mAdapter);
        mNonSwipeableViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        AHBottomNavigationAdapter bottomNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_bottom_bar);
        bottomNavigationAdapter.setupWithBottomNavigation(mBottomNavigation);
        mBottomNavigation.setOnTabSelectedListener(this);

        // Change colors
        mBottomNavigation.setAccentColor(R.color.colorAccent);
        mBottomNavigation.setInactiveColor(R.color.Gray);

    }


    private class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
