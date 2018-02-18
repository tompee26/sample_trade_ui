package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

public class MarketTrade extends BaseObservable {
    private long mTime;
    private double mPrice;
    private double mAmount;
    private double mPriceIndicator;

    @Bindable
    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        if (price - mPrice != 0) {
            mPriceIndicator = price - mPrice;
        }
        mPrice = price;
        notifyPropertyChanged(BR.price);
        notifyPropertyChanged(BR.priceChangeIndicator);
    }

    @Bindable
    public double getPriceChangeIndicator() {
        return mPriceIndicator;
    }

    @Bindable
    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
        notifyPropertyChanged(BR.amount);
    }
}
