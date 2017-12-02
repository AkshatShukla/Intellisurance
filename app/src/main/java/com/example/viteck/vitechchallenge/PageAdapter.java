package com.example.viteck.vitechchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Greg on 12/2/17.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    int numOftabs;

    public PageAdapter (FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numOftabs = numOfTabs;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MapFragment tab1 = new MapFragment();
                return tab1;
            case 1:
                testFragment tab2 = new testFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOftabs;
    }
}
