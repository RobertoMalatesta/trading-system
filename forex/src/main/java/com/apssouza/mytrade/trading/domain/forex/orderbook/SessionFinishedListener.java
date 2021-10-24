package com.apssouza.mytrade.trading.domain.forex.orderbook;

import com.apssouza.mytrade.trading.domain.forex.common.Event;
import com.apssouza.mytrade.trading.domain.forex.common.TradingParams;
import com.apssouza.mytrade.trading.domain.forex.common.observer.PropertyChangeEvent;
import com.apssouza.mytrade.trading.domain.forex.common.observer.PropertyChangeListener;
import com.apssouza.mytrade.trading.domain.forex.session.SessionFinishedEvent;

import java.io.IOException;

class SessionFinishedListener implements PropertyChangeListener {

    private final BookHistoryService historyHandler;

    public SessionFinishedListener(BookHistoryService historyHandler) {
        this.historyHandler = historyHandler;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Event event = (Event) evt.getNewValue();
        if (!(event instanceof SessionFinishedEvent)) {
            return;
        }

        try {
            historyHandler.export(TradingParams.transaction_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished session");
    }

}
