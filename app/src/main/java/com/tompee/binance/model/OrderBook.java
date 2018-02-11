package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class OrderBook extends BaseObservable {
    private int mBidCount;
    private double mBidAmount;
    private int mAskCount;
    private double mAskAmount;

    @Bindable
    public int getBidCount() {
        return mBidCount;
    }

    public void setBidCount(int bidCount) {
        mBidCount = bidCount;
        notifyPropertyChanged(BR.bidCount);
    }

    public double getBidAmount() {
        return mBidAmount;
    }

    @Bindable
    public String getBidAmountText() {
        return getStringFromDouble(mBidAmount);
    }

    public void setBidAmount(double bidAmount) {
        mBidAmount = bidAmount;
        notifyPropertyChanged(BR.bidAmountText);
    }

    private static String getStringFromDouble(double value) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(8);
        df.setMinimumFractionDigits(8);
        return df.format(value);
    }

    @Bindable
    public int getAskCount() {
        return mAskCount;
    }

    public void setAskCount(int askCount) {
        mAskCount = askCount;
    }

    public double getAskAmount() {
        return mAskAmount;
    }

    @Bindable
    public String getAskAmountText() {
        return getStringFromDouble(mAskAmount);
    }

    public void setAskAmount(double askAmount) {
        mAskAmount = askAmount;
    }
}
