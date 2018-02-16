package com.tompee.binance.services.api;

import android.util.Log;

import com.binance.api.client.BinanceApiCallback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tompee.binance.model.AllMarketTickersEvent;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class BinanceWebSocketListener<T> extends WebSocketListener {

    private BinanceApiCallback<T> callback;

    private TypeReference<T> eventTypeReference;

    BinanceWebSocketListener(BinanceApiCallback<T> callback) {
        this.callback = callback;
        this.eventTypeReference = new TypeReference<T>() {
        };
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T event = mapper.readValue(text, new TypeReference<List<AllMarketTickersEvent>>() {});
            callback.onResponse(event);
        } catch (IOException e) {
            Log.d("listener", e.toString());
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e("onFailure", t.toString());
    }
}
