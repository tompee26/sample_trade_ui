package com.tompee.binance.controller.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tompee.binance.MainActivity;
import com.tompee.binance.MarketDetailActivity;
import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.MarketItemAdapter;
import com.tompee.binance.controller.listener.ItemClickListener;
import com.tompee.binance.databinding.FragmentListBinding;
import com.tompee.binance.model.MarketItem;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ItemClickListener.OnItemClickListener, IListFragment{
    public static final String TOKEN_TAG = "refToken";
    private MarketItemAdapter mMarketItemAdapter;

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
        switch (getArguments().getString(TOKEN_TAG)) {
            case "BNB":
                mMarketItemAdapter = new MarketItemAdapter(MainActivity.tokenListBnb);
                break;
            case "BTC":
                mMarketItemAdapter = new MarketItemAdapter(MainActivity.tokenListBtc);
                break;
            case "ETH":
                mMarketItemAdapter = new MarketItemAdapter(MainActivity.tokenListEth);
                break;
            case "USDT":
                mMarketItemAdapter = new MarketItemAdapter(MainActivity.tokenListUsdt);
                break;
            default:
                mMarketItemAdapter = new MarketItemAdapter(new ArrayList<MarketItem>());
                break;
        }
        binding.recyclerView.setAdapter(mMarketItemAdapter);
        return binding.getRoot();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(getContext(), MarketDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MarketDetailActivity.MARKET_DETAIL_TAG, position);
        intent.putExtra(MarketDetailActivity.MARKET_DETAIL_TOKEN, getArguments().getString(TOKEN_TAG));
        getContext().startActivity(intent);
    }

    @Override
    public void sort() {
        if (mMarketItemAdapter != null) {
            mMarketItemAdapter.notifyDataSetChanged();
        }
    }
}
