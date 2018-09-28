package com.apssouza.mytrade.trading.forex.session;

import com.apssouza.mytrade.feed.price.PriceHandler;
import com.apssouza.mytrade.feed.signal.SignalDao;
import com.apssouza.mytrade.feed.signal.SignalDto;
import com.apssouza.mytrade.trading.forex.order.OrderDto;
import com.apssouza.mytrade.trading.forex.portfolio.Portfolio;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class HistoryBookHandler {

    private final Portfolio portfolio;
    private final PriceHandler priceHandler;
    public List<TransactionDto> transactions = new LinkedList<>();

    public HistoryBookHandler(Portfolio portfolio, PriceHandler priceHandler) {
        this.portfolio = portfolio;
        this.priceHandler = priceHandler;
    }

    public List<TransactionDto> getTransactions() {
        return this.transactions;
    }

    public void addSignal(List<SignalDto> signals, List<OrderDto> orders) {

    }

    public void process(LocalDateTime currentTime) {

    }
}
