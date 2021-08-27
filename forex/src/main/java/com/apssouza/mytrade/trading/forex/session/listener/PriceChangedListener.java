package com.apssouza.mytrade.trading.forex.session.listener;

import com.apssouza.mytrade.feed.api.SignalDto;
import com.apssouza.mytrade.trading.forex.execution.OrderExecution;
import com.apssouza.mytrade.trading.forex.signalfeed.SignalFeedHandler;
import com.apssouza.mytrade.trading.forex.order.OrderDto;
import com.apssouza.mytrade.trading.forex.order.OrderHandler;
import com.apssouza.mytrade.trading.forex.order.OrderStatus;
import com.apssouza.mytrade.trading.forex.portfolio.PortfolioHandler;
import com.apssouza.mytrade.trading.forex.session.EventNotifier;
import com.apssouza.mytrade.trading.forex.session.MultiPositionHandler;
import com.apssouza.mytrade.trading.forex.session.event.Event;
import com.apssouza.mytrade.trading.forex.session.event.EventType;
import com.apssouza.mytrade.trading.forex.session.event.OrderFoundEvent;
import com.apssouza.mytrade.trading.forex.session.event.PriceChangedEvent;
import com.apssouza.mytrade.trading.forex.session.event.SignalCreatedEvent;
import com.apssouza.mytrade.trading.misc.helper.TradingParams;
import com.apssouza.mytrade.trading.misc.observer.PropertyChangeEvent;
import com.apssouza.mytrade.trading.misc.observer.PropertyChangeListener;

import java.time.LocalDateTime;
import java.util.List;

public class PriceChangedListener implements PropertyChangeListener {

    private final OrderExecution executionHandler;
    private final PortfolioHandler portfolioHandler;
    private final SignalFeedHandler signalFeedHandler;
    private final OrderHandler orderHandler;
    private final EventNotifier eventNotifier;

    public PriceChangedListener(
            OrderExecution executionHandler,
            PortfolioHandler portfolioHandler,
            SignalFeedHandler signalFeedHandler,
            OrderHandler orderHandler,
            EventNotifier eventNotifier
    ) {
        this.executionHandler = executionHandler;
        this.portfolioHandler = portfolioHandler;
        this.signalFeedHandler = signalFeedHandler;
        this.orderHandler = orderHandler;
        this.eventNotifier = eventNotifier;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Event e = (Event) evt.getNewValue();
        if (!(e instanceof PriceChangedEvent event)) {
            return;
        }
        try {
            process(event);
        } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void process(PriceChangedEvent event) throws InterruptedException {
        LocalDateTime currentTime = event.getTimestamp();
        this.executionHandler.setCurrentTime(currentTime);
        this.executionHandler.setPriceMap(event.getPrice());
        this.portfolioHandler.updatePortfolioValue(event);
        this.portfolioHandler.stopOrderHandle(event);

        List<SignalDto> signals = processSignals(event, currentTime);
        portfolioHandler.processExits(event, signals);
        processOrders(event, currentTime);
    }

    private List<SignalDto> processSignals(PriceChangedEvent event, LocalDateTime currentTime) {
        var signals = this.signalFeedHandler.getSignal(TradingParams.systemName, event.getTimestamp());

        for (SignalDto signal : signals) {
            eventNotifier.notify(new SignalCreatedEvent(
                    EventType.SIGNAL_CREATED,
                    currentTime,
                    event.getPrice(),
                    signal
            ));
        }
        return signals;
    }

    private void processOrders(PriceChangedEvent event, LocalDateTime currentTime) {
        List<OrderDto> orders = this.orderHandler.getOrderByStatus(OrderStatus.CREATED);
        List<OrderDto> orderList = MultiPositionHandler.createPositionIdentifier(orders);

        if (orderList.isEmpty()) {
            return;
        }

        eventNotifier.notify(new OrderFoundEvent(
                EventType.ORDER_FOUND,
                currentTime,
                event.getPrice(),
                orderList
        ));
    }
}
