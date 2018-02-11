package com.tompee.binance.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;

import com.tompee.binance.BR;
import com.tompee.binance.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MarketItem extends BaseObservable {
    private final Context mContext;
    private final String mTokenName;
    private final String mRefTokenName;
    private double mVolume;
    private double mPrice;
    private double mPriceUsd;
    private double mChange;
    private double mPriceChange;
    private double mPriceLow;
    private double mPriceHigh;
    private final List<OrderBook> mOrderBook;

    public MarketItem(Context context, String tokenName, String refTokenName) {
        mContext = context;
        mTokenName = tokenName;
        mRefTokenName = refTokenName;
        mOrderBook = new ArrayList<>();
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
        return getStringFromDouble(mVolume);
    }

    public void setVolume(double volume) {
        mVolume = volume;
        notifyPropertyChanged(BR.volumeText);
    }

    public double getPrice() {
        return mPrice;
    }

    @Bindable
    public String getPriceText() {
        return getStringFromDouble(mPrice);
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

    public double getPriceChange() {
        return mPriceChange;
    }

    @Bindable
    public String getPriceChangeText() {
        if (mPriceChange >= 0) {
            return "+" + getStringFromDouble(mPriceChange);
        }
        return getStringFromDouble(mPriceChange);
    }

    @Bindable
    public int getPriceChangeColor() {
        if (mPriceChange >= 0) {
            return ContextCompat.getColor(mContext, R.color.colorPositiveChange);
        }
        return ContextCompat.getColor(mContext, R.color.colorNegativeChange);
    }

    public void setPriceChange(double priceChange) {
        mPriceChange = priceChange;
        notifyPropertyChanged(BR.priceChangeText);
        notifyPropertyChanged(BR.priceChangeColor);
    }

    private static String getStringFromDouble(double value) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        return df.format(value);
    }

    public double getPriceLow() {
        return mPriceLow;
    }

    @Bindable
    public String getPriceLowText() {
        return getStringFromDouble(mPriceLow);
    }

    public void setPriceLow(double priceLow) {
        mPriceLow = priceLow;
        notifyPropertyChanged(BR.priceLowText);
    }

    public double getPriceHigh() {
        return mPriceHigh;
    }

    @Bindable
    public String getPriceHighText() {
        return getStringFromDouble(mPriceHigh);
    }

    public void setPriceHigh(double priceHigh) {
        mPriceHigh = priceHigh;
        notifyPropertyChanged(BR.priceHighText);
    }

    public List<OrderBook> getOrderBook(){
        return mOrderBook;
    }
}
