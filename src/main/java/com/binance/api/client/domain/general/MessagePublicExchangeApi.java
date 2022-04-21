package com.binance.api.client.domain.general;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePublicExchangeApi {

    private String code;

    private String message;

    private String messageDetail;

    private List<ExchangeProductInfo> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public List<ExchangeProductInfo> getData() {
        return data;
    }

    public void setData(List<ExchangeProductInfo> data) {
        this.data = data;
    }
}