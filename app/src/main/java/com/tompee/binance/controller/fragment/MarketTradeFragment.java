package com.tompee.binance.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binance.api.client.domain.market.AggTrade;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.MarketTradeAdapter;
import com.tompee.binance.databinding.FragmentMarketTradeBinding;
import com.tompee.binance.model.MarketTrade;
import com.tompee.binance.services.api.BinanceWrapper;

import java.util.ArrayList;
import java.util.List;

public class MarketTradeFragment extends Fragment {
    private static final String TOKEN_TAG = "token";
    private final List<MarketTrade> mList = new ArrayList<>();
    private boolean mDestroyFlag;
    private Handler mHandler;
    private MarketTradeAdapter mAdapter;
    private boolean mIsPaused;

    public static MarketTradeFragment getInstance(String token) {
        MarketTradeFragment fragment = new MarketTradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN_TAG, token);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String token = getArguments().getString(TOKEN_TAG);
        FragmentMarketTradeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_trade,
                container, false);

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(binding.
                recyclerView.getContext(), manager.getOrientation());
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.horizontal_alpha_divider));
        binding.recyclerView.addItemDecoration(divider);

        mAdapter = new MarketTradeAdapter(mList);
        binding.recyclerView.setAdapter(mAdapter);

        mHandler = new Handler();
        new Thread(() -> startListening(token)).start();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPaused = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPaused = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDestroyFlag = true;
    }

    private void startListening(String token) {
        while (!mDestroyFlag) {
            if (!mIsPaused) {
                BinanceWrapper.getInstance(getContext()).getMarketTrade(token, trades -> {
                    if (mList.isEmpty()) {
                        for (int index = 15; index > 0; index--) {
                            AggTrade aggTrade = trades.get(index);
                            MarketTrade marketTrade = new MarketTrade();
                            marketTrade.setTime(aggTrade.getTradeTime());
                            marketTrade.setAmount(Double.parseDouble(aggTrade.getQuantity()));

                            // set price twice to trigger price change
                            marketTrade.setPrice(Double.parseDouble(trades.get(index - 1).getPrice()));
                            marketTrade.setPrice(Double.parseDouble(aggTrade.getPrice()));
                            mList.add(marketTrade);
                        }
                        mHandler.post(() -> mAdapter.notifyDataSetChanged());
                    } else {
                        for (int index = 15; index > 0; index--) {
                            AggTrade aggTrade = trades.get(index);
                            MarketTrade marketTrade = mList.get(15 - index);
                            marketTrade.setTime(aggTrade.getTradeTime());
                            marketTrade.setAmount(Double.parseDouble(aggTrade.getQuantity()));

                            // set price twice to trigger price change
                            marketTrade.setPrice(Double.parseDouble(trades.get(index - 1).getPrice()));
                            marketTrade.setPrice(Double.parseDouble(aggTrade.getPrice()));
                        }
                    }
                });
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
