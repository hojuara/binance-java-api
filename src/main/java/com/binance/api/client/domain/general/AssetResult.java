package com.binance.api.client.domain.general;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetResult {

    @JsonProperty("data")
    private List<Asset> data;

    @JsonProperty("status")
    private Status status;

    public List<Asset> getData() {
        return data;
    }

    public void setData(List<Asset> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status {

        @JsonProperty("credit_count")
        private int creditCount;

        @JsonProperty("elapsed")
        private int elapsed;

        @JsonProperty("error_code")
        private int errorCode;

        @JsonProperty("error_message")
        private String errorMessage;

        @JsonProperty("notice")
        private String notice;

        @JsonProperty("timestamp")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime timestamp;

        public int getCreditCount() {
            return creditCount;
        }

        public void setCreditCount(int creditCount) {
            this.creditCount = creditCount;
        }

        public int getElapsed() {
            return elapsed;
        }

        public void setElapsed(int elapsed) {
            this.elapsed = elapsed;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

        private static final long serialVersionUID = 8800245954216770996L;

        protected LocalDateTimeDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String strValue = p.readValueAs(String.class);
            if (strValue != null) {
                return LocalDateTime.parse(strValue, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
            return null;
        }
    }
}