package com.binance.api.client.domain.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Filters define trading rules on a symbol or an exchange. Filters come in two
 * forms: symbol filters and exchange filters.
 *
 * The PRICE_FILTER defines the price rules for a symbol.
 *
 * The LOT_SIZE filter defines the quantity (aka "lots" in auction terms) rules
 * for a symbol.
 *
 * The MIN_NOTIONAL filter defines the minimum notional value allowed for an
 * order on a symbol. An order's notional value is the price * quantity.
 *
 * The MAX_NUM_ORDERS filter defines the maximum number of orders an account is
 * allowed to have open on a symbol. Note that both "algo" orders and normal
 * orders are counted for this filter.
 *
 * The MAX_ALGO_ORDERS filter defines the maximum number of "algo" orders an
 * account is allowed to have open on a symbol. "Algo" orders are STOP_LOSS,
 * STOP_LOSS_LIMIT, TAKE_PROFIT, and TAKE_PROFIT_LIMIT orders.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolFilter {

    // PRICE_FILTER

    private FilterType filterType;

    /**
     * Defines the minimum price/stopPrice allowed.
     */
    private String minPrice;

    /**
     * Defines the maximum price/stopPrice allowed.
     */
    private String maxPrice;

    /**
     * Defines the intervals that a price/stopPrice can be increased/decreased by.
     */
    private String tickSize;

    // LOT_SIZE

    /**
     * Defines the minimum quantity/icebergQty allowed.
     */
    private String minQty;

    /**
     * Defines the maximum quantity/icebergQty allowed.
     */
    private String maxQty;

    /**
     * Defines the intervals that a quantity/icebergQty can be increased/decreased
     * by.
     */
    private String stepSize;

    // NOTIONAL

    /**
     * Defines the minimum and maximum notional value allowed for an order on a symbol. An
     * order's notional value is the price * quantity.
     */
    private String minNotional;
    private String maxNotional;

    // MAX_NUM_ALGO_ORDERS

    /**
     * Defines the maximum number of "algo" orders an account is allowed to have
     * open on a symbol. "Algo" orders are STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT,
     * and TAKE_PROFIT_LIMIT orders.
     */
    private String maxNumAlgoOrders;

    /**
     * MAX_NUM_ORDERS filter defines the maximum number of orders an account is
     * allowed to have open on a symbol. Note that both "algo" orders and normal
     * orders are counted for this filter. MAX_ALGO_ORDERS filter defines the
     * maximum number of "algo" orders an account is allowed to have open on a
     * symbol. "Algo" orders are STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, and
     * TAKE_PROFIT_LIMIT orders. ICEBERG_PARTS filter defines the maximum parts an
     * iceberg order can have. The number of ICEBERG_PARTS is defined as CEIL(qty /
     * icebergQty).
     */
    private String limit;

    /**
     * TRAILING_DELTA filter defines the minimum and maximum value for the parameter trailingDelta.
     * In order for a trailing stop order to pass this filter, the following must be true:
     * 
     * For STOP_LOSS BUY, STOP_LOSS_LIMIT_BUY,TAKE_PROFIT SELL and TAKE_PROFIT_LIMIT SELL orders:
     *  trailingDelta >= minTrailingAboveDelta
     *  trailingDelta <= maxTrailingAboveDelta
     *  
     * For STOP_LOSS SELL, STOP_LOSS_LIMIT SELL, TAKE_PROFIT BUY, and TAKE_PROFIT_LIMIT BUY orders:
     *  trailingDelta >= minTrailingBelowDelta
     *  trailingDelta <= maxTrailingBelowDelta
     */
    private String minTrailingAboveDelta;
    private String maxTrailingAboveDelta;
    private String minTrailingBelowDelta;
    private String maxTrailingBelowDelta;

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }

    public String getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(String maxQty) {
        this.maxQty = maxQty;
    }

    public String getStepSize() {
        return stepSize;
    }

    public void setStepSize(String stepSize) {
        this.stepSize = stepSize;
    }

    public String getMinNotional() {
        return minNotional;
    }

    public void setMinNotional(String minNotional) {
        this.minNotional = minNotional;
    }

    public String getMaxNotional() {
        return maxNotional;
    }

    public void setMaxNotional(String maxNotional) {
        this.maxNotional = maxNotional;
    }

    public String getMaxNumAlgoOrders() {
        return maxNumAlgoOrders;
    }

    public SymbolFilter setMaxNumAlgoOrders(String maxNumAlgoOrders) {
        this.maxNumAlgoOrders = maxNumAlgoOrders;
        return this;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMinTrailingAboveDelta() {
        return minTrailingAboveDelta;
    }

    public void setMinTrailingAboveDelta(String minTrailingAboveDelta) {
        this.minTrailingAboveDelta = minTrailingAboveDelta;
    }

    public String getMaxTrailingAboveDelta() {
        return maxTrailingAboveDelta;
    }

    public void setMaxTrailingAboveDelta(String maxTrailingAboveDelta) {
        this.maxTrailingAboveDelta = maxTrailingAboveDelta;
    }

    public String getMinTrailingBelowDelta() {
        return minTrailingBelowDelta;
    }

    public void setMinTrailingBelowDelta(String minTrailingBelowDelta) {
        this.minTrailingBelowDelta = minTrailingBelowDelta;
    }

    public String getMaxTrailingBelowDelta() {
        return maxTrailingBelowDelta;
    }

    public void setMaxTrailingBelowDelta(String maxTrailingBelowDelta) {
        this.maxTrailingBelowDelta = maxTrailingBelowDelta;
    }
}