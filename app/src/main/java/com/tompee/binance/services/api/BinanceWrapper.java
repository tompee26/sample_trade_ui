package com.tompee.binance.services.api;

import android.content.Context;
import android.util.Log;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.OrderBook;
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
    private final BinanceApiRestClient mRestClient;
    private final BinanceApiAsyncRestClient mAsyncRestClient;
    private final BinanceApiWebSocketClient mWebSocketClient;
    private final OkHttpClient mOkHttpClient;

    private BinanceWrapper(Context context) {
        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        mOkHttpClient = new OkHttpClient.Builder().dispatcher(d).build();

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(context.
                getString(R.string.binance_api_key), context.getString(R.string.binance_api_secret));
        mWebSocketClient = factory.newWebSocketClient();
        mAsyncRestClient = factory.newAsyncRestClient();
        mRestClient = factory.newRestClient();
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

    public void getOrderBook(final String token, final OrderBookListener listener) {
        Log.d(TAG, "Fetching order book");
        final int limit = 20;
        mAsyncRestClient.getOrderBook(token, limit, orderBook -> {
            if (listener != null) {
                listener.onOrderBookUpdate(orderBook);
            }
        });
    }

    public void getMarketTrade(final String token, final MarketTradeListener listener) {
        Log.d(TAG, "Fetching market trade");
        final int limit = 16;
        mAsyncRestClient.getAggTrades(token, null, limit, null, null, trade -> {
            if (listener != null) {
                listener.onMarketTradeUpdate(trade);
            }
        });
    }

    private void startListening() {
        String streamingUrl = String.format("%s/%s", BinanceApiConstants.WS_API_BASE_URL, "!ticker@arr");
        Request request = new Request.Builder().url(streamingUrl).build();
        mOkHttpClient.newWebSocket(request, new BinanceWebSocketListener<List<AllMarketTickersEvent>>(event -> {
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

    public interface OrderBookListener {
        void onOrderBookUpdate(OrderBook book);
    }

    public interface MarketTradeListener {
        void onMarketTradeUpdate(List<AggTrade> trades);
    }
}
