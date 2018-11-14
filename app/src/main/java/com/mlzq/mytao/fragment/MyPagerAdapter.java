package com.mlzq.mytao.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Dev on 2018/8/31.
 * desc :
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] mTitles;
    ArrayList<Fragment>mFragments;

//    public MyPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }

    public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
