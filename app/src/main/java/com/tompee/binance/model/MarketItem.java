package com.tompee.binance.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;

import com.tompee.binance.BR;
import com.tompee.binance.R;

import java.text.NumberFormat;
import java.util.Locale;

public class MarketItem extends BaseObservable {
    private final Context mContext;
    private final String mTokenName;
    private final String mRefTokenName;
    private long mVolume;
    private double mPrice;
    private double mPriceUsd;
    private double mChange;

    public MarketItem(Context context, String tokenName, String refTokenName) {
        mContext = context;
        mTokenName = tokenName;
        mRefTokenName = " / " + refTokenName;
    }

    @Bindable
    public String getTokenName() {
        return mTokenName;
    }

    @Bindable
    public String getRefTokenName() {
        return mRefTokenName;
    }

    public double getVolume() {
        return mVolume;
    }

    @Bindable
    public String getVolumeText() {
        return NumberFormat.getNumberInstance(Locale.US).format(mVolume);
    }

    public void setVolume(long volume) {
        mVolume = volume;
        notifyPropertyChanged(BR.volumeText);
    }

    public double getPrice() {
        return mPrice;
    }

    @Bindable
    public String getPriceText() {
        return String.valueOf(mPrice);
    }

    public void setPrice(double price) {
        mPrice = price;
        notifyPropertyChanged(BR.priceText);
    }

    @Bindable
    public double getPriceUsd() {
        return mPriceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        mPriceUsd = priceUsd;
        notifyPropertyChanged(BR.priceUsd);
    }

    @Bindable
    public String getChangeText() {
        if (mChange >= 0) {
            return "+" + String.valueOf(mChange);
        }
        return String.valueOf(mChange);
    }

    @Bindable
    public int getChangeColor() {
        if (mChange >= 0) {
            return ContextCompat.getColor(mContext, R.color.colorPositiveChange);
        }
        return ContextCompat.getColor(mContext, R.color.colorNegativeChange);
    }

    public double getChange() {
        return mChange;
    }

    public void setChange(double change) {
        mChange = change;
        notifyPropertyChanged(BR.changeText);
        notifyPropertyChanged(BR.changeColor);
    }
}
