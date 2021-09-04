package com.apssouza.mytrade.trading.domain.forex.order;

import com.apssouza.mytrade.feed.api.PriceDto;
import com.apssouza.mytrade.trading.domain.forex.common.AbstractEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderFoundEvent extends AbstractEvent {

    private final List<OrderDto> orders;

    public OrderFoundEvent(LocalDateTime timestamp, Map<String, PriceDto> priceDtoMap, List<OrderDto> orders) {
        super(timestamp, priceDtoMap);
        this.orders = orders;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }
}
