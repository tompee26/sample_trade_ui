package com.tompee.binance.controller.utility;

import android.app.ActionBar;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.tompee.binance.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapterUtils {

    @BindingAdapter({"isVisible"})
    public static void setIsVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"isIncreasingText"})
    public static void setIncreasingSymbolText(TextView view, boolean isIncreasing) {
        if (isIncreasing) {
            view.setText(view.getContext().getString(R.string.increase));
        } else {
            view.setText(view.getContext().getString(R.string.decrease));
        }
    }

    @BindingAdapter({"isIncreasingBackground"})
    public static void setIncreasingBackground(View view, boolean isIncreasing) {
        if (isIncreasing) {
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPositiveChange));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorNegativeChange));
        }
    }

    @BindingAdapter({"priceChangeTextColor"})
    public static void setIncreasingTextColor(TextView view, double d1) {
        if (d1 > 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPositiveChange));
        } else if (d1 < 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorNegativeChange));
        } else {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
        }
    }

    @BindingAdapter({"isIncreasingTextColor"})
    public static void setIncreasingTextColor(TextView view, boolean isIncreasing) {
        if (isIncreasing) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPositiveChange));
        } else {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorNegativeChange));
        }
    }

    @BindingAdapter({"priceChangeTextColorDetail"})
    public static void setIncreasingTextColorDetail(TextView view, double d1) {
        if (d1 > 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPositiveChange));
        } else if (d1 < 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorNegativeChange));
        } else {
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorLightGrey));
        }
    }

    @BindingAdapter({"doubleText"})
    public static void setDoubleText(TextView view, double input) {
        view.setText(getStringFromDouble(input));
    }

    @BindingAdapter({"priceRow"})
    public static void setPriceRowText(TextView view, double input) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(8);
        df.setMinimumFractionDigits(2);
        view.setText(df.format(input));
    }

    @BindingAdapter({"changePercent"})
    public static void setPercentDoubleText(TextView view, double input) {
        double value = Math.round(input * 1000.0) / 1000.0;
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        String text = input >= 0 ? "+" + df.format(value) : df.format(value);
        view.setText(String.format(view.getContext().getString(R.string.market_item_change_format), text));
    }

    @BindingAdapter({"usdPrice"})
    public static void setUsdPriceText(TextView view, double input) {
        double value = Math.round(input * 1000.0) / 1000.0;
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        view.setText(String.format(view.getContext().getString(R.string.market_item_price_usd_format), df.format(value)));
    }

    @BindingAdapter({"changeText"})
    public static void setChangeText(TextView view, double input) {
        Context context = view.getContext();
        String text = input >= 0 ? "+" + input : String.valueOf(input);
        view.setText(String.format(context.getString(R.string.market_item_change_format), text));
    }

    @BindingAdapter({"rowVolume"})
    public static void setVolumeRowText(TextView view, double input) {
        DecimalFormat df = new DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(0);
        view.setText(String.format(view.getContext().getString(R.string.market_item_volume_format), df.format(input)));
    }

    @BindingAdapter({"rowVolumeDetail", "refToken"})
    public static void setVolumeRowDetailText(TextView view, double input, String refToken) {
        DecimalFormat df = new DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        view.setText(String.format(view.getContext().getString(R.string.market_detail_volume_format), df.format(input), refToken));
    }

    @BindingAdapter({"priceChangeSymbol"})
    public static void setPriceSymbolVisibility(View view, double input) {
        view.setVisibility(input == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @BindingAdapter({"time"})
    public static void setTime(TextView view, long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("HH:mm:ss");
        view.setText(format.format(date));
    }

    @BindingAdapter({"customWidth"})
    public static void setWidth(View view, double input) {
        int width = (int)(input * ((View)view.getParent()).getWidth());
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }


    private static String getStringFromDouble(double value) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(8);
        return df.format(value);
    }
}
