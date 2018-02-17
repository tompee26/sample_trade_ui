package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MarketItem extends BaseObservable {
    private final String mTokenName;
    private final String mRefTokenName;
    private final List<OrderBook> mOrderBook;
    private double mVolume;
    private double mPrice;
    private double mPriceUsd;
    private double mChange;
    private double mPriceChange;
    private double mPriceChangeIndicator;
    private double mPriceLow;
    private double mPriceHigh;

    public MarketItem(String tokenName, String refTokenName) {
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

    @Bindable
    public double getVolume() {
        return mVolume;
    }

    public void setVolume(double volume) {
        mVolume = volume;
        notifyPropertyChanged(BR.volume);
    }

    @Bindable
    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
        notifyPropertyChanged(BR.price);
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
    public double getChange() {
        return mChange;
    }

    public void setChange(double change) {
        mChange = change;
        notifyPropertyChanged(BR.change);
    }

    @Bindable
    public double getPriceChangeIndicator() {
        return mPriceChangeIndicator;
    }

    @Bindable
    public double getPriceChange() {
        return mPriceChange;
    }

    public void setPriceChange(double priceChange) {
        mPriceChangeIndicator = priceChange - mPriceChange;
        mPriceChange = priceChange;
        notifyPropertyChanged(BR.priceChange);
        notifyPropertyChanged(BR.priceChangeIndicator);
    }

    @Bindable
    public double getPriceLow() {
        return mPriceLow;
    }

    public void setPriceLow(double priceLow) {
        mPriceLow = priceLow;
        notifyPropertyChanged(BR.priceLow);
    }

    @Bindable
    public double getPriceHigh() {
        return mPriceHigh;
    }

    public void setPriceHigh(double priceHigh) {
        mPriceHigh = priceHigh;
        notifyPropertyChanged(BR.priceHigh);
    }

    public List<OrderBook> getOrderBook() {
        return mOrderBook;
    }
}
