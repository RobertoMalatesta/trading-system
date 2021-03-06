package com.apssouza.mytrade.trading.forex.session;

import com.apssouza.mytrade.trading.forex.order.OrderDto;
import com.apssouza.mytrade.trading.forex.portfolio.FilledOrderDto;
import com.apssouza.mytrade.trading.forex.portfolio.Position;
import com.apssouza.mytrade.trading.forex.statistics.TransactionState;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CycleHistory {

    private final LocalDateTime time;

    private Map<String, TransactionDto> transactions = new HashMap<>();

    public CycleHistory(LocalDateTime time) {
        this.time = time;
    }

    public void setState(TransactionState state, String identifier) {
        getTransaction(identifier).setState(state);
    }

    private TransactionDto getTransaction(String identifier) {
        if (transactions.containsKey(identifier)) {
            return transactions.get(identifier);
        }
        TransactionDto transactionDto = new TransactionDto(this.time, identifier);
        transactions.put(identifier, transactionDto);
        return transactions.get(identifier);

    }

    public void addPosition(Position ps) {
        getTransaction(ps.getIdentifier()).setPosition(ps);
    }

    public void addOrderFilled(FilledOrderDto order) {
        getTransaction(order.getIdentifier()).setFilledOrder(order);
    }

    public void addOrder(OrderDto order) {
        getTransaction(order.getIdentifier()).setOrder(order);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Map<String, TransactionDto> getTransactions() {
        return transactions;
    }
}
