package com.tompee.binance.controller.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tompee.binance.BR;
import com.tompee.binance.R;
import com.tompee.binance.model.MarketTrade;
import com.tompee.binance.model.OrderBook;

import java.util.List;

public class MarketTradeAdapter extends RecyclerView.Adapter<MarketTradeAdapter.MarketTradeViewHolder> {
    private final List<MarketTrade> mMarketTrades;

    public MarketTradeAdapter(List<MarketTrade> orderBookList) {
        mMarketTrades = orderBookList;
    }

    @Override
    public MarketTradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_market_trade, parent, false);
        return new MarketTradeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MarketTradeViewHolder holder, int position) {
        final MarketTrade item = mMarketTrades.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mMarketTrades.size();
    }

    class MarketTradeViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding mBinding;

        MarketTradeViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(MarketTrade trade) {
            mBinding.setVariable(BR.marketTrade, trade);
            mBinding.executePendingBindings();
        }
    }
}
