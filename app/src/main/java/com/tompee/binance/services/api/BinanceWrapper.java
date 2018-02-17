package com.tompee.binance.services.api;

import android.content.Context;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.tompee.binance.R;
import com.tompee.binance.model.AllMarketTickersEvent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BinanceWrapper {
    private static final String TAG = "BinanceWrapper";
    private static BinanceWrapper mWrapperInstance;
    private final List<MarketUpdateListener> mListener;
    private BinanceApiRestClient mClient;
    private BinanceApiAsyncRestClient mAsyncClient;

    private BinanceWrapper(Context context) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(context.
                getString(R.string.binance_api_key), context.getString(R.string.binance_api_secret));
        mClient = factory.newRestClient();
        mAsyncClient = factory.newAsyncRestClient();
        mListener = new ArrayList<>();
        startListening();
    }

    public static BinanceWrapper getInstance(Context context) {
        if (mWrapperInstance == null) {
            mWrapperInstance = new BinanceWrapper(context);
        }
        return mWrapperInstance;
    }

    public void addMarketUpdateHandler(MarketUpdateListener listener) {
        mListener.add(listener);
    }

    public void remoteUpdateHandler(MarketUpdateListener listener) {
        mListener.remove(listener);
    }

    private void startListening() {
        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        OkHttpClient httpClient = new OkHttpClient.Builder().dispatcher(d).build();
        String streamingUrl = String.format("%s/%s", BinanceApiConstants.WS_API_BASE_URL, "!ticker@arr");
        Request request = new Request.Builder().url(streamingUrl).build();
        httpClient.newWebSocket(request, new BinanceWebSocketListener<List<AllMarketTickersEvent>>(event -> {
            for (MarketUpdateListener listener : mListener) {
                for (AllMarketTickersEvent ticker : event) {
                    if (!listener.onMarketTickerUpdate(ticker)) {
                        break;
                    }
                }
            }
        }));
    }

    public interface MarketUpdateListener {
        boolean onMarketTickerUpdate(AllMarketTickersEvent event);
    }
}
