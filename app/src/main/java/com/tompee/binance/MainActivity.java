package com.tompee.binance;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tompee.binance.controller.adapter.MarketPageAdapter;
import com.tompee.binance.databinding.ActivityMainBinding;
import com.tompee.binance.model.MarketItem;
import com.tompee.binance.model.OrderBook;
import com.tompee.binance.model.SortInfo;
import com.tompee.binance.services.SortManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final List<MarketItem> tokenListFavorite = new ArrayList<>();
    public static final List<MarketItem> tokenListBnb = new ArrayList<>();
    public static final List<MarketItem> tokenListBtc = new ArrayList<>();
    public static final List<MarketItem> tokenListEth = new ArrayList<>();
    public static final List<MarketItem> tokenListUsdt = new ArrayList<>();

    private ActivityMainBinding mBinding;
    private SortManager mSortManager;
    private SortInfo mSortInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSortManager = new SortManager();
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        mBinding.tabLayoutMain.setupWithViewPager(mBinding.viewPager);
        LoadDataTask task = new LoadDataTask(this,
                new MarketPageAdapter(this, getSupportFragmentManager()));
        task.execute();

        mSortInfo = new SortInfo();
        setSortInfo();
        mBinding.setSort(mSortInfo);

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

    /* For dummy data generation only */
    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        private final Context mContext;
        private final MarketPageAdapter mAdapter;

        LoadDataTask(Context context, MarketPageAdapter adapter) {
            mContext = context;
            mAdapter = adapter;
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
            mBinding.viewPager.setAdapter(mAdapter);
        }
    }
}
