package com.tompee.binance;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tompee.binance.controller.adapter.MarketPageAdapter;
import com.tompee.binance.databinding.ActivityMainBinding;
import com.tompee.binance.model.MarketItem;
import com.tompee.binance.model.SortManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {
    public static final Map<String, MarketItem> tokenMap = new HashMap<>();

    private ActivityMainBinding mBinding;
    private SortManager mSortManager;
    private MarketPageAdapter mMarketPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSortManager = new SortManager();
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        mBinding.setSortManager(mSortManager);
        mBinding.tabLayoutMain.setupWithViewPager(mBinding.viewPager);
        mMarketPageAdapter = new MarketPageAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.addOnPageChangeListener(this);
        mBinding.viewPager.setOffscreenPageLimit(mMarketPageAdapter.getCount());
        mBinding.viewPager.setAdapter(mMarketPageAdapter);

        onClick(mBinding.vol);
        mBinding.pair.setOnClickListener(this);
        mBinding.vol.setOnClickListener(this);
        mBinding.ltp.setOnClickListener(this);
        mBinding.percentChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pair:
                mSortManager.changeToPairVol(SortManager.SortType.PAIR);
                mBinding.pair.setSelected(mSortManager.getSortType() == SortManager.SortType.PAIR);
                mBinding.vol.setSelected(mSortManager.getSortType() == SortManager.SortType.VOL);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.vol:
                mSortManager.changeToPairVol(SortManager.SortType.VOL);
                mBinding.pair.setSelected(mSortManager.getSortType() == SortManager.SortType.PAIR);
                mBinding.vol.setSelected(mSortManager.getSortType() == SortManager.SortType.VOL);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.ltp:
                mSortManager.changeToLastPrice();
                mBinding.pair.setSelected(false);
                mBinding.vol.setSelected(false);
                mBinding.ltp.setSelected(true);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.percentChange:
                mSortManager.changeToPercentChanged();
                mBinding.pair.setSelected(false);
                mBinding.vol.setSelected(false);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(true);
                break;
        }
        onPageSelected(mBinding.viewPager.getCurrentItem());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class SortTask extends AsyncTask<Void, Void, Void> {
        private final List<MarketItem> mMarketItemList;
        private final int mPosition;

        SortTask(List<MarketItem> marketItemList, int position) {
            mMarketItemList = marketItemList;
            mPosition = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (mSortManager.getSortType()) {
                case PAIR:
                    Collections.sort(mMarketItemList, (l1, l2) -> l1.getTokenName().compareTo(l2.getTokenName()));
                    break;
                case VOL:
                    Collections.sort(mMarketItemList, (l1, l2) -> -compareDouble(l1.getVolume(), l2.getVolume()));
                    break;
                case LAST_PRICE_ASCENDING:
                    Collections.sort(mMarketItemList, (l1, l2) -> compareDouble(l1.getPrice(), l2.getPrice()));
                    break;
                case LAST_PRICE_DESCENDING:
                    Collections.sort(mMarketItemList, (l1, l2) -> -compareDouble(l1.getPrice(), l2.getPrice()));
                    break;
                case PERCENT_CHANGE_ASCENDING:
                    Collections.sort(mMarketItemList, (l1, l2) -> compareDouble(l1.getChange(), l2.getChange()));
                    break;
                case PERCENT_CHANGE_DESCENDING:
                    Collections.sort(mMarketItemList, (l1, l2) -> -compareDouble(l1.getChange(), l2.getChange()));
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new Handler().postDelayed(() -> mMarketPageAdapter.sort(mPosition), 200);
        }

        private int compareDouble(double d1, double d2) {
            if (d1 > d2) return 1;
            if (d1 < d2) return -1;
            return 0;
        }
    }
}
