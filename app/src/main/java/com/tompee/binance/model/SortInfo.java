package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

public class SortInfo extends BaseObservable{
    private String mPairText;
    private int mPairTextColorId;
    private String mVolText;
    private int mVolTextColorId;
    private String mLastPriceText;
    private int mLastPriceTextColorId;
    private String mPercentChangedText;
    private int mPercentChangedColorId;

    @Bindable
    public String getPairText() {
        return mPairText;
    }

    public void setPairText(String pairText) {
        mPairText = pairText;
        notifyPropertyChanged(BR.pairText);
    }

    @Bindable
    public int getPairTextColorId() {
        return mPairTextColorId;
    }

    public void setPairTextColorId(int pairTextColorId) {
        mPairTextColorId = pairTextColorId;
        notifyPropertyChanged(BR.pairTextColorId);
    }

    @Bindable
    public String getVolText() {
        return mVolText;
    }

    public void setVolText(String volText) {
        mVolText = volText;
        notifyPropertyChanged(BR.volText);
    }

    @Bindable
    public int getVolTextColorId() {
        return mVolTextColorId;
    }

    public void setVolTextColorId(int volTextColorId) {
        mVolTextColorId = volTextColorId;
        notifyPropertyChanged(BR.volTextColorId);
    }

    @Bindable
    public String getLastPriceText() {
        return mLastPriceText;
    }

    public void setLastPriceText(String lastPriceText) {
        mLastPriceText = lastPriceText;
        notifyPropertyChanged(BR.lastPriceText);
    }

    @Bindable
    public int getLastPriceTextColorId() {
        return mLastPriceTextColorId;
    }

    public void setLastPriceTextColorId(int lastPriceTextColorId) {
        mLastPriceTextColorId = lastPriceTextColorId;
        notifyPropertyChanged(BR.lastPriceTextColorId);
    }

    @Bindable
    public String getPercentChangedText() {
        return mPercentChangedText;
    }

    public void setPercentChangedText(String percentChangedText) {
        mPercentChangedText = percentChangedText;
        notifyPropertyChanged(BR.percentChangedText);
    }

    @Bindable
    public int getPercentChangedColorId() {
        return mPercentChangedColorId;
    }

    public void setPercentChangedColorId(int percentChangedColorId) {
        mPercentChangedColorId = percentChangedColorId;
        notifyPropertyChanged(BR.percentChangedColorId);
    }
}
