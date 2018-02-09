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

import com.tompee.binance.R;
import com.tompee.binance.controller.adapter.MarketItemAdapter;
import com.tompee.binance.databinding.FragmentListBinding;
import com.tompee.binance.model.MarketItem;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

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

        List<MarketItem> list = new ArrayList<>();
        MarketItem item = new MarketItem(getContext(),"AE", "BNB");
        item.setVolume(13000);
        item.setPrice(0.0021);
        item.setChange(28);
        list.add(item);
        item = new MarketItem(getContext(),"ADA", "BNB");
        item.setVolume(203);
        item.setPrice(13.2);
        item.setChange(-12);
        list.add(item);

        MarketItemAdapter adapter = new MarketItemAdapter(list);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}
