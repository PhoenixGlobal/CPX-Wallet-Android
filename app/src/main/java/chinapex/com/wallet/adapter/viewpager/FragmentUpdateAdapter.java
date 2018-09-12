package chinapex.com.wallet.adapter.viewpager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chinapex.com.wallet.base.BaseFragment;

/**
 * Created by SteelCabbage on 2018/7/24 0024 10:24.
 * E-Mail：liuyi_61@163.com
 */

public class FragmentUpdateAdapter extends MyFragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<String> mTags;
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public FragmentUpdateAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mTags = new ArrayList<>();
        mFragments = fragments;
        mTitles = titles;
    }

    // Fragment的实例化工作
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return null == mFragments ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null == mTitles ? "" : mTitles.get(position);
    }

    // 返回PagerAdapter.POSITION_NONE保证调用notifyDataSetChanged刷新Fragment。
    @Override
    public int getItemPosition(Object object) {
        // 必须返回的是POSITION_NONE，否则不会刷新
        return POSITION_NONE;
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    // 必须重写此方法，添加tag一一做记录
    @Nullable
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mTags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    // 根据tag查找缓存的fragment，移除缓存的fragment，替换成新的
    public void setNewFragments() {
        if (mTags != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (int i = 0; i < mTags.size(); i++) {
                if (i != 0) {
                    fragmentTransaction.remove(mFragmentManager.findFragmentByTag(mTags.get(i)));
                }
            }
            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
            mTags.clear();
        }
        notifyDataSetChanged();
    }

}
