package com.apssouza.mytrade.trading.domain.forex.risk.stoporder;

import com.apssouza.mytrade.feed.api.PriceDto;
import com.apssouza.mytrade.trading.domain.forex.common.AbstractEvent;

import java.time.LocalDateTime;
import java.util.Map;

public class StopOrderFilledEvent extends AbstractEvent {
    private final StopOrderDto order;

    public StopOrderFilledEvent(
            LocalDateTime timestamp,
            Map<String, PriceDto> price,
            StopOrderDto order
    ) {
        super(timestamp, price);
        this.order = order;
    }

    public StopOrderDto getStopOrder() {
        return order;
    }
}
