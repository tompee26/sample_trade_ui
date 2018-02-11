package com.tompee.binance.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tompee.binance.MainActivity;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.MarketItemAdapter;
import com.tompee.binance.controller.adapter.OrderBidAdapter;
import com.tompee.binance.databinding.FragmentOrderBookBinding;
import com.tompee.binance.model.MarketItem;
import com.tompee.binance.model.OrderBook;

import java.util.ArrayList;
import java.util.List;

public class OrderBookFragment extends Fragment {
    private static final String TOKEN_TAG = "token";
    private static final String POSITION_TAG = "position";

    public static OrderBookFragment getInstance(String token, int position) {
        OrderBookFragment fragment = new OrderBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN_TAG, token);
        bundle.putInt(POSITION_TAG, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        int position = getArguments().getInt(POSITION_TAG);
        List<OrderBook> orderBookList;
        switch (getArguments().getString(TOKEN_TAG)) {
            case "BNB":
                orderBookList = MainActivity.tokenListBnb.get(position).getOrderBook();
                break;
            case "BTC":
                orderBookList = MainActivity.tokenListBtc.get(position).getOrderBook();
                break;
            case "ETH":
                orderBookList = MainActivity.tokenListEth.get(position).getOrderBook();
                break;
            case "USDT":
                orderBookList = MainActivity.tokenListUsdt.get(position).getOrderBook();
                break;
            default:
                orderBookList = MainActivity.tokenListFavorite.get(position).getOrderBook();
                break;
        }

        OrderBidAdapter bidAdapter = new OrderBidAdapter(orderBookList, true);
        binding.bidRecyclerView.setAdapter(bidAdapter);

        OrderBidAdapter askAdapter = new OrderBidAdapter(orderBookList, false);
        binding.askRecyclerView.setAdapter(askAdapter);

        return binding.getRoot();
    }
}
