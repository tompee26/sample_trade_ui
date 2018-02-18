package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class OrderBook extends BaseObservable {
    private double mBidCount;
    private double mBidAmount;
    private double mBidRelativeQty = 1;
    private double mAskCount;
    private double mAskAmount;
    private double mAskRelativeQty;

    @Bindable
    public double getBidCount() {
        return mBidCount;
    }

    public void setBidCount(double bidCount) {
        mBidCount = bidCount;
        notifyPropertyChanged(BR.bidCount);
    }

    @Bindable
    public double getBidAmount() {
        return mBidAmount;
    }

    public void setBidAmount(double bidAmount) {
        mBidAmount = bidAmount;
        notifyPropertyChanged(BR.bidAmount);
    }

    @Bindable
    public double getAskCount() {
        return mAskCount;
    }

    public void setAskCount(double askCount) {
        mAskCount = askCount;
        notifyPropertyChanged(BR.askCount);
    }

    @Bindable
    public double getAskAmount() {
        return mAskAmount;
    }

    public void setAskAmount(double askAmount) {
        mAskAmount = askAmount;
        notifyPropertyChanged(BR.askAmount);
    }

    @Bindable
    public double getBidRelativeQty() {
        return mBidRelativeQty;
    }

    public void setBidRelativeQty(double bidRelativeQty) {
        mBidRelativeQty = 1 - bidRelativeQty;
        notifyPropertyChanged(BR.bidRelativeQty);
    }

    @Bindable
    public double getAskRelativeQty() {
        return mAskRelativeQty;
    }

    public void setAskRelativeQty(double askRelativeQty) {
        mAskRelativeQty = askRelativeQty;
        notifyPropertyChanged(BR.askRelativeQty);
    }
}
