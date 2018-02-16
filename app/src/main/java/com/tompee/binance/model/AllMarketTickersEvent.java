package com.tompee.binance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(
        ignoreUnknown = true
)

public class AllMarketTickersEvent {
    @JsonProperty("e")
    private String eventType;
    @JsonProperty("E")
    private long eventTime;
    @JsonProperty("s")
    private String symbol;
    @JsonProperty("p")
    private double priceChange;
    @JsonProperty("P")
    private double priceChangePercent;
    @JsonProperty("w")
    private double weightedAveragePrice;
    @JsonProperty("x")
    private double previousDaysClosePrice;
    @JsonProperty("c")
    private double currentDaysClosePrice;
    @JsonProperty("Q")
    private double closeTradesQuantity;
    @JsonProperty("b")
    private double bestBidPrice;
    @JsonProperty("B")
    private double bestBidQuantity;
    @JsonProperty("a")
    private double bestAskPrice;
    @JsonProperty("A")
    private double bestAskQuantity;
    @JsonProperty("o")
    private double openPrice;
    @JsonProperty("h")
    private double highPrice;
    @JsonProperty("l")
    private double lowPrice;
    @JsonProperty("v")
    private double totalTradedBaseAssetVolume;
    @JsonProperty("q")
    private double totalTradedQuoteAssetVolume;
    @JsonProperty("O")
    private long statisticesOpenTime;
    @JsonProperty("C")
    private long statisticesCloseTime;
    @JsonProperty("F")
    private long firstTradeId;
    @JsonProperty("L")
    private long lastTradeId;
    @JsonProperty("n")
    private long totalNumberOfTrades;

    public AllMarketTickersEvent() {
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPriceChange() {
        return this.priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getPriceChangePercent() {
        return this.priceChangePercent;
    }

    public void setPriceChangePercent(double priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public double getWeightedAveragePrice() {
        return this.weightedAveragePrice;
    }

    public void setWeightedAveragePrice(double weightedAveragePrice) {
        this.weightedAveragePrice = weightedAveragePrice;
    }

    public double getPreviousDaysClosePrice() {
        return this.previousDaysClosePrice;
    }

    public void setPreviousDaysClosePrice(double previousDaysClosePrice) {
        this.previousDaysClosePrice = previousDaysClosePrice;
    }

    public double getCurrentDaysClosePrice() {
        return this.currentDaysClosePrice;
    }

    public void setCurrentDaysClosePrice(double currentDaysClosePrice) {
        this.currentDaysClosePrice = currentDaysClosePrice;
    }

    public double getCloseTradesQuantity() {
        return this.closeTradesQuantity;
    }

    public void setCloseTradesQuantity(double closeTradesQuantity) {
        this.closeTradesQuantity = closeTradesQuantity;
    }

    public double getBestAskPrice() {
        return this.bestAskPrice;
    }

    public void setBestAskPrice(double bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public double getBestAskQuantity() {
        return this.bestAskQuantity;
    }

    public void setBestAskQuantity(double bestAskQuantity) {
        this.bestAskQuantity = bestAskQuantity;
    }

    public double getOpenPrice() {
        return this.openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return this.highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return this.lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getTotalTradedBaseAssetVolume() {
        return this.totalTradedBaseAssetVolume;
    }

    public void setTotalTradedBaseAssetVolume(double totalTradedBaseAssetVolume) {
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
    }

    public double getTotalTradedQuoteAssetVolume() {
        return this.totalTradedQuoteAssetVolume;
    }

    public void setTotalTradedQuoteAssetVolume(double totalTradedQuoteAssetVolume) {
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    public long getStatisticesOpenTime() {
        return this.statisticesOpenTime;
    }

    public void setStatisticesOpenTime(long statisticesOpenTime) {
        this.statisticesOpenTime = statisticesOpenTime;
    }

    public long getStatisticesCloseTime() {
        return this.statisticesCloseTime;
    }

    public void setStatisticesCloseTime(long statisticesCloseTime) {
        this.statisticesCloseTime = statisticesCloseTime;
    }

    public long getFirstTradeId() {
        return this.firstTradeId;
    }

    public void setFirstTradeId(long firstTradeId) {
        this.firstTradeId = firstTradeId;
    }

    public long getLastTradeId() {
        return this.lastTradeId;
    }

    public void setLastTradeId(long lastTradeId) {
        this.lastTradeId = lastTradeId;
    }

    public long getTotalNumberOfTrades() {
        return this.totalNumberOfTrades;
    }

    public void setTotalNumberOfTrades(long totalNumberOfTrades) {
        this.totalNumberOfTrades = totalNumberOfTrades;
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).append("eventType", this.eventType).append("eventTime", this.eventTime).append("symbol", this.symbol).append("priceChange", this.priceChange).append("priceChangePercent", this.priceChangePercent).append("weightedAveragePrice", this.weightedAveragePrice).append("previousDaysClosePrice", this.previousDaysClosePrice).append("currentDaysClosePrice", this.currentDaysClosePrice).append("closeTradesQuantity", this.closeTradesQuantity).append("bestAskPrice", this.bestAskPrice).append("bestAskQuantity", this.bestAskQuantity).append("openPrice", this.openPrice).append("highPrice", this.highPrice).append("lowPrice", this.lowPrice).append("totalTradedBaseAssetVolume", this.totalTradedBaseAssetVolume).append("totalTradedQuoteAssetVolume", this.totalTradedQuoteAssetVolume).append("statisticesOpenTime", this.statisticesOpenTime).append("statisticesCloseTime", this.statisticesCloseTime).append("firstTradeId", this.firstTradeId).append("lastTradeId", this.lastTradeId).append("totalNumberOfTrades", this.totalNumberOfTrades).toString();
    }

    public double getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(double bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public double getBestBidQuantity() {
        return bestBidQuantity;
    }

    public void setBestBidQuantity(double bestBidQuantity) {
        this.bestBidQuantity = bestBidQuantity;
    }
}
