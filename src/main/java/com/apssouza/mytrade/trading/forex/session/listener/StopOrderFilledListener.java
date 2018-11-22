package com.apssouza.mytrade.trading.forex.session.listener;

import com.apssouza.mytrade.trading.forex.order.StopOrderDto;
import com.apssouza.mytrade.trading.forex.portfolio.ExitReason;
import com.apssouza.mytrade.trading.forex.portfolio.FilledOrderDto;
import com.apssouza.mytrade.trading.forex.portfolio.Portfolio;
import com.apssouza.mytrade.trading.forex.portfolio.Position;
import com.apssouza.mytrade.trading.forex.statistics.HistoryBookHandler;
import com.apssouza.mytrade.trading.forex.session.MultiPositionHandler;
import com.apssouza.mytrade.trading.forex.session.event.Event;
import com.apssouza.mytrade.trading.forex.session.event.StopOrderFilledEvent;
import com.apssouza.mytrade.trading.forex.statistics.TransactionState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class StopOrderFilledListener implements PropertyChangeListener {

    private final Portfolio portfolio;
    private final HistoryBookHandler historyHandler;

    public StopOrderFilledListener(Portfolio portfolio, HistoryBookHandler historyHandler) {
        this.portfolio = portfolio;
        this.historyHandler = historyHandler;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Event event = (Event) evt.getNewValue();
        if (!(event instanceof StopOrderFilledEvent)) {
            return;
        }
        StopOrderFilledEvent orderFilledEvent = (StopOrderFilledEvent) event;
        StopOrderDto stopOrder = orderFilledEvent.getStopOrder();
        LocalDateTime time = orderFilledEvent.getTimestamp();
        Position ps = MultiPositionHandler.getPositionByStopOrder(stopOrder);
        ps.closePosition(ExitReason.STOP_ORDER_FILLED);
        this.portfolio.closePosition(ps.getIdentifier());
        this.historyHandler.setState(TransactionState.EXIT, ps.getIdentifier());
        this.historyHandler.addPosition(ps);

        this.historyHandler.addOrderFilled(new FilledOrderDto(
                time,
                stopOrder.getSymbol(),
                stopOrder.getAction(),
                stopOrder.getQuantity(),
                stopOrder.getFilledPrice(),
                ps.getIdentifier(),
                stopOrder.getId()
        ));
    }
}
