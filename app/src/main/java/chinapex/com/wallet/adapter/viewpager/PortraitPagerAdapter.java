package chinapex.com.wallet.adapter.viewpager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;

import java.util.List;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseFragment;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.FragmentFactory;

/**
 * Created by SteelCabbage on 2018/7/24 0024 10:24.
 * E-Mail：liuyi_61@163.com
 */

public class PortraitPagerAdapter extends MyFragmentPagerAdapter {
    private static final String TAG = PortraitPagerAdapter.class.getSimpleName();
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public PortraitPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String>
            titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return null == mFragments ? 0 : mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null == mTitles ? "" : mTitles.get(position);
    }

}
