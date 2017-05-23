package com.hsmerseburg.mariia.popularmovies1.adapters;

/**
 * Created by 2mdenyse on 28.03.2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hsmerseburg.mariia.popularmovies1.fragments.InformationFragment;
import com.hsmerseburg.mariia.popularmovies1.fragments.ReviewsFragment;
import com.hsmerseburg.mariia.popularmovies1.fragments.TrailersFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private final Bundle fragmentBundle;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Bundle bundle) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        fragmentBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InformationFragment tab1 = new InformationFragment();
                tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                TrailersFragment tab2 = new TrailersFragment();
                tab2.setArguments(this.fragmentBundle);
                return tab2;
            case 2:
                ReviewsFragment tab3 = new ReviewsFragment();
                tab3.setArguments(this.fragmentBundle);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
