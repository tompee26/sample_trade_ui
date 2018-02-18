package com.tompee.binance.controller.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tompee.binance.BR;
import com.tompee.binance.MainActivity;
import com.tompee.binance.MarketDetailActivity;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.MarketItemAdapter;
import com.tompee.binance.controller.listener.ItemClickListener;
import com.tompee.binance.databinding.FragmentListBinding;
import com.tompee.binance.model.AllMarketTickersEvent;
import com.tompee.binance.model.MarketItem;
import com.tompee.binance.services.api.BinanceWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment implements ItemClickListener.OnItemClickListener,
        BinanceWrapper.MarketUpdateListener {
    public static final String TOKEN_TAG = "refToken";
    private final List<MarketItem> mTokenList = new ArrayList<>();
    private String mRefToken;
    private MarketItemAdapter mMarketItemAdapter;
    private Handler mHandler;

    public static ListFragment getInstance(String refToken) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN_TAG, refToken);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list,
                container, false);
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(),
                manager.getOrientation());
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider));
        binding.recyclerView.addItemDecoration(divider);

        ItemClickListener.addTo(binding.recyclerView).setOnItemClickListener(this);
        mRefToken = getArguments().getString(TOKEN_TAG);
        mMarketItemAdapter = new MarketItemAdapter(mTokenList);
        binding.recyclerView.setAdapter(mMarketItemAdapter);

        mHandler = new Handler();
        MainActivity.sortManager.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (i == BR.sortType && getUserVisibleHint()) {
                    sort();
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ListFragment", "Adding market update handler");
        BinanceWrapper.getInstance(getContext()).addMarketUpdateHandler(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        MarketItem item = mTokenList.get(position);
        Intent intent = new Intent(getContext(), MarketDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MarketDetailActivity.MARKET_DETAIL_TOKEN,
                item.getTokenName() + item.getRefTokenName());
        getContext().startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sort();
        }
    }

    public void sort() {
        switch (MainActivity.sortManager.getSortType()) {
            case PAIR:
                Collections.sort(mTokenList, (l1, l2) -> l1.getTokenName().compareTo(l2.getTokenName()));
                break;
            case VOL:
                Collections.sort(mTokenList, (l1, l2) -> -compareDouble(l1.getVolume(), l2.getVolume()));
                break;
            case LAST_PRICE_ASCENDING:
                Collections.sort(mTokenList, (l1, l2) -> compareDouble(l1.getPrice(), l2.getPrice()));
                break;
            case LAST_PRICE_DESCENDING:
                Collections.sort(mTokenList, (l1, l2) -> -compareDouble(l1.getPrice(), l2.getPrice()));
                break;
            case PERCENT_CHANGE_ASCENDING:
                Collections.sort(mTokenList, (l1, l2) -> compareDouble(l1.getChange(), l2.getChange()));
                break;
            case PERCENT_CHANGE_DESCENDING:
                Collections.sort(mTokenList, (l1, l2) -> -compareDouble(l1.getChange(), l2.getChange()));
                break;
        }
        if (mMarketItemAdapter != null) {
            mMarketItemAdapter.notifyDataSetChanged();
        }
    }

    private int compareDouble(double d1, double d2) {
        if (d1 > d2) return 1;
        if (d1 < d2) return -1;
        return 0;
    }

    @Override
    public void onMarketTickerUpdate(AllMarketTickersEvent event) {
        if (!event.getSymbol().endsWith(mRefToken)) {
            return;
        }

        MarketItem item;
        boolean isExisting;
        if (MainActivity.tokenMap.containsKey(event.getSymbol())) {
            item = MainActivity.tokenMap.get(event.getSymbol());
            isExisting = true;
        } else {
            item = new MarketItem(event.getSymbol().replace(mRefToken, ""), mRefToken);
            isExisting = false;
        }
        item.setChange(event.getPriceChangePercent());
        item.setPrice(event.getCurrentDaysClosePrice());
        item.setPriceChange(event.getPriceChange());
        item.setVolume(event.getTotalTradedQuoteAssetVolume());
        item.setPriceLow(event.getLowPrice());
        item.setPriceHigh(event.getHighPrice());

        if (mRefToken.equals("USDT")) {
            item.setPriceUsd(item.getPrice());
        } else {
            MarketItem usdtRef = MainActivity.tokenMap.get(mRefToken + "USDT");
            if (usdtRef != null) {
                item.setPriceUsd(usdtRef.getPrice() * item.getPrice());
            }
        }

        if (!isExisting) {
            mTokenList.add(item);
            MainActivity.tokenMap.put(event.getSymbol(), item);
            mHandler.post(() -> {
                sort();
                mMarketItemAdapter.notifyDataSetChanged();
            });
        }
    }
}
