package com.tompee.binance;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tompee.binance.controller.adapter.MarketPageAdapter;
import com.tompee.binance.databinding.ActivityMainBinding;
import com.tompee.binance.model.SortInfo;
import com.tompee.binance.services.SortManager;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private SortManager mSortManager;
    private SortInfo mSortInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSortManager = new SortManager();
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        binding.viewPager.setAdapter(new MarketPageAdapter(this, getSupportFragmentManager()));
        binding.tabLayoutMain.setupWithViewPager(binding.viewPager);

        mSortInfo = new SortInfo();
        setSortInfo();
        binding.setSort(mSortInfo);

        binding.pair.setOnClickListener(this);
        binding.vol.setOnClickListener(this);
        binding.ltp.setOnClickListener(this);
        binding.percentChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pair:
                mSortManager.changeToPairVol(SortManager.SortType.PAIR);
                break;
            case R.id.vol:
                mSortManager.changeToPairVol(SortManager.SortType.VOL);
                break;
            case R.id.ltp:
                mSortManager.changeToLastPrice();
                break;
            case R.id.percentChange:
                mSortManager.changeToPercentChanged();
                break;
        }
        setSortInfo();
    }

    private void setSortInfo() {
        switch (mSortManager.getSortType()) {
            case PAIR:
                mSortInfo.setPairText(getString(R.string.market_pair) + "↑");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorAccent));
                mSortInfo.setVolText(" / " + getString(R.string.market_vol));
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price));
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change));
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                break;
            case VOL:
                mSortInfo.setPairText(getString(R.string.market_pair) + " / ");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setVolText(getString(R.string.market_vol) + "↓");
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorAccent));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price));
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change));
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                break;
            case LAST_PRICE_ASCENDING:
                mSortInfo.setPairText(getString(R.string.market_pair) + " / ");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setVolText(getString(R.string.market_vol));
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price) + "↑");
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorAccent));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change));
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                break;
            case LAST_PRICE_DESCENDING:
                mSortInfo.setPairText(getString(R.string.market_pair) + " / ");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setVolText(getString(R.string.market_vol));
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price) + "↓");
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorAccent));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change));
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                break;
            case PERCENT_CHANGE_ASCENDING:
                mSortInfo.setPairText(getString(R.string.market_pair) + " / ");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setVolText(getString(R.string.market_vol));
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price));
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change) + "↑");
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            case PERCENT_CHANGE_DESCENDING:
                mSortInfo.setPairText(getString(R.string.market_pair) + " / ");
                mSortInfo.setPairTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setVolText(getString(R.string.market_vol));
                mSortInfo.setVolTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setLastPriceText(getString(R.string.market_last_price));
                mSortInfo.setLastPriceTextColorId(ContextCompat.getColor(this, R.color.colorLightGrey));
                mSortInfo.setPercentChangedText(getString(R.string.market_pair_24h_change) + "↓");
                mSortInfo.setPercentChangedColorId(ContextCompat.getColor(this, R.color.colorAccent));
                break;
        }
    }
}
