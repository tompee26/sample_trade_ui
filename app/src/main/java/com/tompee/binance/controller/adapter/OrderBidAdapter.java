package com.tompee.binance.controller.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tompee.binance.BR;
import com.tompee.binance.R;
import com.tompee.binance.model.OrderBook;

import java.util.List;

public class OrderBidAdapter extends RecyclerView.Adapter<OrderBidAdapter.OrderBidViewHolder> {
    private final List<OrderBook> mOrderBookList;
    private final boolean mIsBid;

    public OrderBidAdapter(List<OrderBook> orderBookList, boolean isBid) {
        mOrderBookList = orderBookList;
        mIsBid = isBid;
    }

    @Override
    public OrderBidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mIsBid ?
                R.layout.row_order_bid : R.layout.row_ask_bid, parent, false);
        return new OrderBidAdapter.OrderBidViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(OrderBidViewHolder holder, int position) {
        final OrderBook item = mOrderBookList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mOrderBookList.size();
    }

    class OrderBidViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding mBinding;

        OrderBidViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(OrderBook orderBook) {
            mBinding.setVariable(BR.orderBook, orderBook);
            mBinding.executePendingBindings();
        }
    }
}
