package com.tompee.binance;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.tompee.binance.controller.adapter.MarketPageAdapter;
import com.tompee.binance.databinding.ActivityMainBinding;
import com.tompee.binance.model.MarketItem;
import com.tompee.binance.model.SortManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final Map<String, MarketItem> tokenMap = new HashMap<>();
    public static final SortManager sortManager = new SortManager();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        mBinding.setSortManager(sortManager);
        mBinding.tabLayoutMain.setupWithViewPager(mBinding.viewPager);
        MarketPageAdapter marketPageAdapter = new MarketPageAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.setOffscreenPageLimit(1);
        mBinding.viewPager.setAdapter(marketPageAdapter);

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
                sortManager.changeToPairVol(SortManager.SortType.PAIR);
                mBinding.pair.setSelected(sortManager.getSortType() == SortManager.SortType.PAIR);
                mBinding.vol.setSelected(sortManager.getSortType() == SortManager.SortType.VOL);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.vol:
                sortManager.changeToPairVol(SortManager.SortType.VOL);
                mBinding.pair.setSelected(sortManager.getSortType() == SortManager.SortType.PAIR);
                mBinding.vol.setSelected(sortManager.getSortType() == SortManager.SortType.VOL);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.ltp:
                sortManager.changeToLastPrice();
                mBinding.pair.setSelected(false);
                mBinding.vol.setSelected(false);
                mBinding.ltp.setSelected(true);
                mBinding.percentChange.setSelected(false);
                break;
            case R.id.percentChange:
                sortManager.changeToPercentChanged();
                mBinding.pair.setSelected(false);
                mBinding.vol.setSelected(false);
                mBinding.ltp.setSelected(false);
                mBinding.percentChange.setSelected(true);
                break;
        }
    }
}
