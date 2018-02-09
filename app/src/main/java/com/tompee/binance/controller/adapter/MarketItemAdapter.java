package com.tompee.binance.controller.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tompee.binance.BR;
import com.tompee.binance.R;
import com.tompee.binance.model.MarketItem;

import java.util.List;

public class MarketItemAdapter extends RecyclerView.Adapter<MarketItemAdapter.MarketItemViewHolder> {
    private List<MarketItem> mMarketItemList;

    public MarketItemAdapter(List<MarketItem> marketItemList) {
        mMarketItemList = marketItemList;
    }

    @Override
    public MarketItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_market_item, parent, false);
        return new MarketItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MarketItemViewHolder holder, int position) {
        final MarketItem item = mMarketItemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mMarketItemList.size();
    }

    class MarketItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding mBinding;

        MarketItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(MarketItem marketItem) {
            mBinding.setVariable(BR.marketItem, marketItem);
            mBinding.executePendingBindings();
        }
    }
}
