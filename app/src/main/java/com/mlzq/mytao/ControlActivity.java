package com.mlzq.mytao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mlzq.mytao.fragment.FirstFragment;
import com.mlzq.mytao.fragment.MyPagerAdapter;
import com.mlzq.mytao.fragment.SecondMainFragment;
import com.mlzq.mytao.fragment.SimpleCardFragment;
import com.mlzq.mytao.fragment.TabEntity;
import com.mlzq.mytao.http.UpdateUtile;
import com.mlzq.nubiolib.app.BaseActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlActivity extends BaseActivity {
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tl_2)
    CommonTabLayout mBottomBar2;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();//图标
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

    private String[] mTitles = {"导购", "精选", "待定", "待定"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_control);
        ButterKnife.bind(this);
        final Random mRandom = new Random();
        for (int i=0;i<mTitles.length;i++) {
            String title=mTitles[i];
            switch (i){
                case 0:
                    mFragments.add(new FirstFragment());
                    break;
                case 1:
                    mFragments.add(new SecondMainFragment());
                    break;
                default:
                    mFragments.add(SimpleCardFragment.getInstance(title));
                    break;
            }

            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBottomBar2.setTabData(mTabEntities);
                mBottomBar2.setOnTabSelectListener(new OnTabSelectListener() {
                    @Override
                    public void onTabSelect(int position) {
                        mViewPager.setCurrentItem(position);
                    }

                    @Override
                    public void onTabReselect(int position) {
                        if (position == 0) {
                            mBottomBar2.showMsg(0, mRandom.nextInt(100) + 1);
                        }
                    }
                });
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        UpdateUtile.getServiceVersion(this);
    }








}
