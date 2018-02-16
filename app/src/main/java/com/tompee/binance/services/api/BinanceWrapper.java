package com.tompee.binance.services.api;

import android.content.Context;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import com.tompee.binance.R;
import com.tompee.binance.model.AllMarketTickersEvent;
import com.tompee.binance.model.MarketItem;

import java.util.List;
import java.util.Map;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BinanceWrapper {
    private static final String TAG = "BinanceWrapper";
    private static BinanceWrapper mInstance;
    private BinanceApiRestClient mClient;
    private MarketUpdateListener mListener;

    private BinanceWrapper(Context context) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(context.
                getString(R.string.binance_api_key), context.getString(R.string.binance_api_secret));
        mClient = factory.newRestClient();

        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().dispatcher(d).build();
        String streamingUrl = String.format("%s/%s", BinanceApiConstants.WS_API_BASE_URL, "!ticker@arr");
        Request request = new Request.Builder().url(streamingUrl).build();
        okHttpClient.newWebSocket(request, new BinanceWebSocketListener<List<AllMarketTickersEvent>>(event -> {
            if (mListener != null) {
                for (AllMarketTickersEvent item : event) {
                    mListener.onUpdate(item);
                }
            }
        }));
    }

    public static BinanceWrapper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BinanceWrapper(context);
        }
        return mInstance;
    }

    public void setMarketUpdateHandler(MarketUpdateListener listener) {
        mListener = listener;
    }

    public void getAllPrices(List<MarketItem> bnbItems, List<MarketItem> btcItems,
                             List<MarketItem> ethItems, List<MarketItem> usdtItems,
                             Map<String, MarketItem> tokenMap) {
        List<TickerPrice> allPrices = mClient.getAllPrices();

        /* Parse ticker price object */
        for (TickerPrice ticker : allPrices) {
            if (ticker.getSymbol().endsWith("BNB")) {
                addMarketItem(ticker, "BNB", bnbItems, tokenMap);
            } else if (ticker.getSymbol().endsWith("BTC")) {
                addMarketItem(ticker, "BTC", btcItems, tokenMap);
            } else if (ticker.getSymbol().endsWith("ETH")) {
                addMarketItem(ticker, "ETH", ethItems, tokenMap);
            } else if (ticker.getSymbol().endsWith("USDT")) {
                addMarketItem(ticker, "USDT", usdtItems, tokenMap);
            }
        }
    }

    private void addMarketItem(TickerPrice ticker, String refToken,
                               List<MarketItem> list, Map<String, MarketItem> map) {
        MarketItem item = new MarketItem(ticker.getSymbol().replace(refToken, ""), refToken);
        item.setPrice(Double.parseDouble(ticker.getPrice()));
        list.add(item);
        map.put(ticker.getSymbol(), item);
    }

    public void getStats(Map<String, MarketItem> tokenMap) {
        for (Map.Entry<String, MarketItem> entry : tokenMap.entrySet())
        {
            TickerStatistics tickerStatistics = mClient.get24HrPriceStatistics(entry.getKey());
            MarketItem item = entry.getValue();
            item.setChange(Double.parseDouble(tickerStatistics.getPriceChangePercent()));
            item.setVolume(Double.parseDouble(tickerStatistics.getVolume()));
            item.setPriceHigh(Double.parseDouble(tickerStatistics.getHighPrice()));
            item.setPriceLow(Double.parseDouble(tickerStatistics.getLowPrice()));
        }
    }

    public interface MarketUpdateListener {
        void onUpdate(AllMarketTickersEvent event);
    }
}
