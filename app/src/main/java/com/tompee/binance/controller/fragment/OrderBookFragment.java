package com.tompee.binance.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binance.api.client.domain.market.OrderBookEntry;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.OrderBidAdapter;
import com.tompee.binance.databinding.FragmentOrderBookBinding;
import com.tompee.binance.model.OrderBook;
import com.tompee.binance.services.api.BinanceWrapper;

import java.util.ArrayList;
import java.util.List;

public class OrderBookFragment extends Fragment {
    private static final String TOKEN_TAG = "token";
    private final List<OrderBook> mOrderBookList = new ArrayList<>();
    private boolean mDestroyFlag;
    private Handler mHandler;
    private OrderBidAdapter mOrderAdapter;
    private OrderBidAdapter mAskAdapter;
    private boolean mIsPaused;

    public static OrderBookFragment getInstance(String token) {
        OrderBookFragment fragment = new OrderBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN_TAG, token);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String token = getArguments().getString(TOKEN_TAG);
        FragmentOrderBookBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_book,
                container, false);
        binding.bidRecyclerView.setHasFixedSize(true);
        binding.askRecyclerView.setHasFixedSize(true);

        LinearLayoutManager bidManager = new LinearLayoutManager(getContext());
        binding.bidRecyclerView.setLayoutManager(bidManager);
        LinearLayoutManager askManager = new LinearLayoutManager(getContext());
        binding.askRecyclerView.setLayoutManager(askManager);

        DividerItemDecoration divider = new DividerItemDecoration(binding.bidRecyclerView.getContext(), bidManager.getOrientation());
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.horizontal_alpha_divider));
        binding.bidRecyclerView.addItemDecoration(divider);
        binding.askRecyclerView.addItemDecoration(divider);

        mHandler = new Handler();
        mOrderAdapter = new OrderBidAdapter(mOrderBookList, true);
        binding.bidRecyclerView.setAdapter(mOrderAdapter);

        mAskAdapter = new OrderBidAdapter(mOrderBookList, false);
        binding.askRecyclerView.setAdapter(mAskAdapter);

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

    private void startListening(String token) {
        while (!mDestroyFlag) {
            if (!mIsPaused) {
                BinanceWrapper.getInstance(getContext()).getOrderBook(token, book -> {
                    double maxBidQty = 0, maxAskQty = 0;
                    for (int index = 0; index < 15; index++) {
                        double ask = Double.parseDouble(book.getAsks().get(index).getQty());
                        double bid = Double.parseDouble(book.getBids().get(index).getQty());
                        maxBidQty = maxBidQty > bid ? maxBidQty : bid;
                        maxAskQty = maxAskQty > ask ? maxAskQty : ask;
                    }

                    if (mOrderBookList.isEmpty()) {
                        for (int index = 0; index < 15; index++) {
                            OrderBookEntry ask = book.getAsks().get(index);
                            OrderBookEntry bid = book.getBids().get(index);

                            OrderBook entry = new OrderBook();
                            entry.setAskAmount(Double.parseDouble(ask.getPrice()));
                            entry.setAskCount(Double.parseDouble(ask.getQty()));
                            entry.setAskRelativeQty(entry.getAskCount() / maxAskQty);
                            entry.setBidAmount(Double.parseDouble(bid.getPrice()));
                            entry.setBidCount(Double.parseDouble(bid.getQty()));
                            entry.setBidRelativeQty(entry.getBidCount() / maxBidQty);
                            mOrderBookList.add(entry);
                        }
                        mHandler.post(() -> {
                            mOrderAdapter.notifyDataSetChanged();
                            mAskAdapter.notifyDataSetChanged();
                        });
                    } else {
                        for (int index = 0; index < 15; index++) {
                            OrderBookEntry ask = book.getAsks().get(index);
                            OrderBookEntry bid = book.getBids().get(index);

                            OrderBook entry = mOrderBookList.get(index);
                            entry.setAskAmount(Double.parseDouble(ask.getPrice()));
                            entry.setAskCount(Double.parseDouble(ask.getQty()));
                            entry.setAskRelativeQty(entry.getAskCount() / maxAskQty);
                            entry.setBidAmount(Double.parseDouble(bid.getPrice()));
                            entry.setBidCount(Double.parseDouble(bid.getQty()));
                            entry.setBidRelativeQty(entry.getBidCount() / maxBidQty);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDestroyFlag = true;
    }
}
