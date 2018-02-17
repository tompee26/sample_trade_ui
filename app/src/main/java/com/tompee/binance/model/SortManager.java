package com.tompee.binance.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tompee.binance.BR;

public class SortManager extends BaseObservable {

    public enum SortType {
        PAIR,
        VOL,
        LAST_PRICE_ASCENDING,
        LAST_PRICE_DESCENDING,
        PERCENT_CHANGE_ASCENDING,
        PERCENT_CHANGE_DESCENDING
    }

    private SortType mSortType = SortType.VOL;

    @Bindable
    public SortType getSortType() {
        return mSortType;
    }

    public void changeToPairVol(SortType sortType) {
        if (mSortType == SortType.PAIR || mSortType == SortType.VOL) {
            mSortType = mSortType == SortType.PAIR ? SortType.VOL : SortType.PAIR;
        } else {
            mSortType = sortType;
        }
        notifyPropertyChanged(BR.sortType);
    }

    public void changeToLastPrice() {
        if (mSortType == SortType.LAST_PRICE_ASCENDING) {
            mSortType = SortType.LAST_PRICE_DESCENDING;
        }
        else {
            mSortType = SortType.LAST_PRICE_ASCENDING;
        }
        notifyPropertyChanged(BR.sortType);
    }

    public void changeToPercentChanged() {
        if (mSortType == SortType.PERCENT_CHANGE_ASCENDING) {
            mSortType = SortType.PERCENT_CHANGE_DESCENDING;
        } else {
            mSortType = SortType.PERCENT_CHANGE_ASCENDING;
        }
        notifyPropertyChanged(BR.sortType);
    }
}
