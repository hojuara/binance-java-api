package com.binance.api.client.impl;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.domain.event.AllMarketTickersEvent;
import com.binance.api.client.domain.event.BookDepthEvent;
import com.binance.api.client.domain.event.BookTickerEvent;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.event.DepthEvent;
import com.binance.api.client.domain.event.UserDataUpdateEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.fasterxml.jackson.core.type.TypeReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Binance API WebSocket client implementation using OkHttp.
 */
public class BinanceApiWebSocketClientImpl implements BinanceApiWebSocketClient {

    private final OkHttpClient client;

    public BinanceApiWebSocketClientImpl(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public Closeable onDepthEvent(String symbols, BinanceApiCallback<DepthEvent> callback) {
        final String channel = Arrays.stream(symbols.split(","))
                .map(String::trim)
                .map(s -> String.format("%s@depth@100ms", s))
                .collect(Collectors.joining("/"));
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, DepthEvent.class));
    }

    @Override
    public Closeable onCandlestickEvent(String symbols, CandlestickInterval interval,
            BinanceApiCallback<CandlestickEvent> callback) {
        final String channel = Arrays.stream(symbols.split(","))
                .map(String::trim)
                .map(s -> String.format("%s@kline_%s", s, interval.getIntervalId()))
                .collect(Collectors.joining("/"));
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, CandlestickEvent.class));
    }

    public Closeable onAggTradeEvent(String symbols, BinanceApiCallback<AggTradeEvent> callback) {
        final String channel = Arrays.stream(symbols.split(","))
                .map(String::trim)
                .map(s -> String.format("%s@aggTrade", s))
                .collect(Collectors.joining("/"));
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, AggTradeEvent.class));
    }

    public Closeable onUserDataUpdateEvent(String listenKey, BinanceApiCallback<UserDataUpdateEvent> callback) {
        return createNewWebSocket(listenKey, new BinanceApiWebSocketListener<>(callback, UserDataUpdateEvent.class));
    }

    public Closeable onAllMarketTickersEvent(BinanceApiCallback<List<AllMarketTickersEvent>> callback) {
        final String channel = "!ticker@arr";
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, new TypeReference<List<AllMarketTickersEvent>>() {}));
    }

    @Override
    public Closeable onPartialDepthEvent(String symbol, int limit, BinanceApiCallback<BookDepthEvent> callback) {
        final String channel = String.format("%s@depth@100ms", symbol, limit);
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, BookDepthEvent.class));
    }

    @Override
    public Closeable onBookTickerEvent(String symbols, BinanceApiCallback<BookTickerEvent> callback) {
        final String channel = Arrays.stream(symbols.split(","))
                .map(String::trim)
                .map(s -> String.format("%s@bookTicker", s))
                .collect(Collectors.joining("/"));
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, BookTickerEvent.class));
    }

    /**
     * @deprecated This method is no longer functional. Please use the returned
     *             {@link Closeable} from any of the other methods to close the web
     *             socket.
     */
    @Override
    public void close() {
    }

    private Closeable createNewWebSocket(String channel, BinanceApiWebSocketListener<?> listener) {
        String streamingUrl = String.format("%s/%s", BinanceApiConstants.WS_API_BASE_URL, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        return () -> {
            final int code = 1000;
            final String reason = "";
            listener.onClosing(webSocket, code, reason);
            webSocket.close(code, reason);
            listener.onClosed(webSocket, code, reason);
        };
    }
}
