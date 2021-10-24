package com.apssouza.mytrade.trading.domain.forex.orderbook;

import com.apssouza.mytrade.trading.domain.forex.common.observer.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

public class BookHistoryHandlerFactory {

    public static BookHistoryService create() {
        return new BookHistoryService(new TransactionsExporter());
    }

    public static List<PropertyChangeListener> createListeners(
            BookHistoryService bookHandler
    ) {
        var listeners = new ArrayList<PropertyChangeListener>();
        listeners.add(new HistoryStopOrderFilledListener(bookHandler));
        listeners.add(new HistoryFilledOrderListener(bookHandler));
        listeners.add(new OrderCreatedListener(bookHandler));
        listeners.add(new SessionFinishedListener(bookHandler));
        listeners.add(new HistoryPortfolioChangedListener(bookHandler));
        return listeners;
    }
}
