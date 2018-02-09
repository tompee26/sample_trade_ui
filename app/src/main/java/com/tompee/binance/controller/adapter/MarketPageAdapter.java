package com.tompee.binance.controller.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tompee.binance.R;
import com.tompee.binance.controller.fragment.ListFragment;

public class MarketPageAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 5;
    private Context mContext;

    public MarketPageAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new ListFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name;
        switch (position) {
            case 0:
                name = mContext.getString(R.string.market_tab_favorite);
                break;
            case 1:
                name = mContext.getString(R.string.market_tab_bnb);
                break;
            case 2:
                name = mContext.getString(R.string.market_tab_btc);
                break;
            case 3:
                name = mContext.getString(R.string.market_tab_eth);
                break;
            default:
                name = mContext.getString(R.string.market_tab_usdt);
                break;
        }
        return name;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
