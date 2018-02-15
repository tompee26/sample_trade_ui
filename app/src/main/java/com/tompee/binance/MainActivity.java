package com.tompee.binance;

import android.content.Context;
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
import com.tompee.binance.model.OrderBook;
import com.tompee.binance.model.SortManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {
    public static final List<MarketItem> tokenListFavorite = new ArrayList<>();
    public static final List<MarketItem> tokenListBnb = new ArrayList<>();
    public static final List<MarketItem> tokenListBtc = new ArrayList<>();
    public static final List<MarketItem> tokenListEth = new ArrayList<>();
    public static final List<MarketItem> tokenListUsdt = new ArrayList<>();

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
        LoadDataTask task = new LoadDataTask(this);
        task.execute();

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
        SortTask task;
        switch (position) {
            case 0:
                task = new SortTask(tokenListFavorite, position);
                break;
            case 1:
                task = new SortTask(tokenListBnb, position);
                break;
            case 2:
                task = new SortTask(tokenListBtc, position);
                break;
            case 3:
                task = new SortTask(tokenListEth, position);
                break;
            default:
                task = new SortTask(tokenListUsdt, position);
                break;
        }
        task.execute();
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

    /* For dummy data generation only */
    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        private final Context mContext;

        LoadDataTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String json;
            try {
                InputStream is = getAssets().open("data.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

            try {
                JSONObject obj = new JSONObject(json);
                JSONArray refArray = obj.getJSONArray("data");
                for (int index = 0; index < refArray.length(); index++) {
                    String refToken = refArray.getJSONObject(index).getString("refToken");
                    JSONArray tokenArray = refArray.getJSONObject(index).getJSONArray("token");
                    for (int tokenIndex = 0; tokenIndex < tokenArray.length(); tokenIndex++) {
                        String tokenName = tokenArray.getJSONObject(tokenIndex).getString("name");
                        MarketItem marketItem = new MarketItem(mContext, tokenName, refToken);
                        JSONObject info = tokenArray.getJSONObject(tokenIndex).getJSONObject("info");
                        marketItem.setVolume(info.getDouble("volume"));
                        marketItem.setPrice(info.getDouble("price"));
                        marketItem.setPriceUsd(info.getDouble("priceUSD"));
                        marketItem.setPriceChange(info.getDouble("priceChange"));
                        marketItem.setChange(info.getDouble("priceChangePercent"));
                        marketItem.setPriceLow(info.getDouble("low"));
                        marketItem.setPriceHigh(info.getDouble("high"));

                        for (int orderBookIndex = 0; orderBookIndex < 15; orderBookIndex++) {
                            OrderBook orderBook = new OrderBook();
                            orderBook.setBidCount(getRandomAmount());
                            orderBook.setBidAmount(marketItem.getPrice() * (.97 - orderBookIndex * 0.01));
                            orderBook.setAskCount(getRandomAmount());
                            orderBook.setAskAmount(marketItem.getPrice() * (.103 + orderBookIndex * 0.01));
                            marketItem.getOrderBook().add(orderBook);
                        }

                        switch (refToken) {
                            case "BNB":
                                tokenListBnb.add(marketItem);
                                break;
                            case "BTC":
                                tokenListBtc.add(marketItem);
                                break;
                            case "ETH":
                                tokenListEth.add(marketItem);
                                break;
                            case "USDT":
                                tokenListUsdt.add(marketItem);
                                break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private int getRandomAmount() {
            Random rand = new Random();
            return (int) (Math.abs(rand.nextGaussian()) * 10000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mBinding.viewPager.setAdapter(mMarketPageAdapter);
        }
    }
}
