package com.example.viteck.viteckchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Greg on 12/2/17.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    int numOftabs;
    CardFragment cardFrag;

    public PageAdapter (FragmentManager fm, int numOfTabs, CardFragment cardFrag)
    {
        super(fm);
        this.numOftabs = numOfTabs;
        this.cardFrag = cardFrag;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MapFragment tab1 = new MapFragment();
                return tab1;
            case 1:
                return cardFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOftabs;
    }
}
