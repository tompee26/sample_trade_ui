package com.tompee.binance;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tompee.binance.controller.adapter.MarketItemAdapter;
import com.tompee.binance.controller.fragment.OrderBookFragment;
import com.tompee.binance.databinding.ActivityMarketDetailBinding;
import com.tompee.binance.model.MarketItem;

public class MarketDetailActivity extends AppCompatActivity {
    public static final String MARKET_DETAIL_TAG = "market_detail";
    public static final String MARKET_DETAIL_TOKEN = "market_token";

    private ActivityMarketDetailBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_market_detail);

        MarketItem item;
        String token = getIntent().getStringExtra(MARKET_DETAIL_TOKEN);
        int position = getIntent().getIntExtra(MARKET_DETAIL_TAG, 0);
        switch (token) {
            case "BNB":
                item = MainActivity.tokenListBnb.get(position);
                break;
            case "BTC":
                item = MainActivity.tokenListBtc.get(position);
                break;
            case "ETH":
                item = MainActivity.tokenListEth.get(position);
                break;
            default:
                item = MainActivity.tokenListUsdt.get(position);
                break;
        }

        mBinding.toolbarView.toolbarText.setText(String.format(getString(R.string.market_detail_toolbar_format),
                item.getTokenName(), item.getRefTokenName()));
        mBinding.toolbarView.toolbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.setMarketItem(item);

        mBinding.orderBook.setOnClickListener(new OrderBookClickListener(this));
        mBinding.marketTrades.setOnClickListener(new MarketTradeClickListener(this));
        mBinding.time.setOnClickListener(new TimeClickListener(this));
        mBinding.depth.setOnClickListener(new DepthClickListener(this));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.orderTradeContainer, OrderBookFragment.getInstance(token, position));
        transaction.commit();
    }

    class OrderBookClickListener implements View.OnClickListener {
        private final Context mContext;

        OrderBookClickListener(Context context) {
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            mBinding.orderBook.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mBinding.orderBook.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelectedOrderTrade));
            mBinding.marketTrades.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            mBinding.marketTrades.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    class MarketTradeClickListener implements View.OnClickListener {
        private final Context mContext;

        MarketTradeClickListener(Context context) {
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            mBinding.marketTrades.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mBinding.marketTrades.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelectedOrderTrade));
            mBinding.orderBook.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            mBinding.orderBook.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    class TimeClickListener implements View.OnClickListener {
        private final Context mContext;

        TimeClickListener(Context context) {
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            mBinding.time.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mBinding.depth.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            Drawable drawable = getResources().getDrawable(R.drawable.kline);
            mBinding.graph.setBackground(drawable);
        }
    }

    class DepthClickListener implements View.OnClickListener {
        private final Context mContext;

        DepthClickListener(Context context) {
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            mBinding.depth.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mBinding.time.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            Drawable drawable = getResources().getDrawable(R.drawable.depth);
            mBinding.graph.setBackground(drawable);
        }
    }
}
