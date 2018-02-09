package com.tompee.binance.services;

public class SortManager {

    public enum SortType {
        PAIR,
        VOL,
        LAST_PRICE_ASCENDING,
        LAST_PRICE_DESCENDING,
        PERCENT_CHANGE_ASCENDING,
        PERCENT_CHANGE_DESCENDING
    }

    private SortType mSortType = SortType.VOL;

    public SortType getSortType() {
        return mSortType;
    }

    public void changeToPairVol(SortType sortType) {
        if (mSortType == SortType.PAIR || mSortType == SortType.VOL) {
            mSortType = mSortType == SortType.PAIR ? SortType.VOL : SortType.PAIR;
            return;
        }
        mSortType = sortType;
    }

    public void changeToLastPrice() {
        if (mSortType == SortType.LAST_PRICE_ASCENDING) {
            mSortType = SortType.LAST_PRICE_DESCENDING;
            return;
        }
        mSortType = SortType.LAST_PRICE_ASCENDING;
    }

    public void changeToPercentChanged() {
        if (mSortType == SortType.PERCENT_CHANGE_ASCENDING) {
            mSortType = SortType.PERCENT_CHANGE_DESCENDING;
            return;
        }
        mSortType = SortType.PERCENT_CHANGE_ASCENDING;
    }
}
