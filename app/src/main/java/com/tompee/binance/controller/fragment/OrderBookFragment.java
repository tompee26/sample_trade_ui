package com.tompee.binance.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binance.api.client.domain.event.DepthEvent;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.OrderBidAdapter;
import com.tompee.binance.databinding.FragmentOrderBookBinding;
import com.tompee.binance.model.OrderBook;
import com.tompee.binance.services.api.BinanceWrapper;

import java.util.ArrayList;
import java.util.List;

public class OrderBookFragment extends Fragment implements BinanceWrapper.DepthListener {
    private static final String TOKEN_TAG = "token";

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

        BinanceWrapper.getInstance(getContext()).startOrderBookListening(token, this);
        List<OrderBook> orderBookList = new ArrayList<>();
        OrderBidAdapter bidAdapter = new OrderBidAdapter(orderBookList, true);
        binding.bidRecyclerView.setAdapter(bidAdapter);

        OrderBidAdapter askAdapter = new OrderBidAdapter(orderBookList, false);
        binding.askRecyclerView.setAdapter(askAdapter);

        return binding.getRoot();
    }

    @Override
    public void onDepthUpdate(DepthEvent event) {
        Log.d("orderbook", event.toString());
    }
}
