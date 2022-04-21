package com.binance.api.client.domain.general;

import com.binance.api.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeProductInfo {

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("st")
    private SymbolStatus status;

    @JsonProperty("b")
    private String buyAsset;

    @JsonProperty("q")
    private String quoteAsset;

    @JsonProperty("ts")
    private String tradeStep;

    @JsonProperty("an")
    private String assetName;
    
    @JsonProperty("qn")
    private String quoteName;

    @JsonProperty("o")
    private String open;

    @JsonProperty("h")
    private String high;

    @JsonProperty("l")
    private String low;

    @JsonProperty("c")
    private String close;
    
    @JsonProperty("v")
    private String volume;

    @JsonProperty("qv")
    private String quoteVolume;

    @JsonProperty("cs")
    private Long circulationSupply;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public SymbolStatus getStatus() {
        return status;
    }

    public void setStatus(SymbolStatus status) {
        this.status = status;
    }

    public String getBuyAsset() {
        return buyAsset;
    }

    public void setBuyAsset(String buyAsset) {
        this.buyAsset = buyAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public String getTradeStep() {
        return tradeStep;
    }

    public void setTradeStep(String tradeStep) {
        this.tradeStep = tradeStep;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public Long getCirculationSupply() {
        return circulationSupply;
    }

    public void setCirculationSupply(Long circulationSupply) {
        this.circulationSupply = circulationSupply;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
            .append("symbol", symbol)
            .append("status", status)
            .append("buyAsset", buyAsset)
            .append("quoteAsset", quoteAsset)
            .append("tradeStep", tradeStep)
            .append("assetName", assetName)
            .append("quoteName", quoteName)
            .append("open", open)
            .append("high", high)
            .append("low", low)
            .append("close", close)
            .append("volume", volume)
            .append("quoteVolume", quoteVolume)
            .append("circulationSupply", circulationSupply)
            .toString();
    }
}