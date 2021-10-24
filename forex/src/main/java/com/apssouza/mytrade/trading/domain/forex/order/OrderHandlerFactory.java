package com.apssouza.mytrade.trading.domain.forex.order;

import com.apssouza.mytrade.trading.domain.forex.common.observer.PropertyChangeListener;
import com.apssouza.mytrade.trading.domain.forex.broker.BrokerService;
import com.apssouza.mytrade.trading.domain.forex.portfolio.PortfolioModel;
import com.apssouza.mytrade.trading.domain.forex.risk.RiskManagementService;
import com.apssouza.mytrade.trading.domain.forex.session.EventNotifier;

import java.util.ArrayList;
import java.util.List;

public class OrderHandlerFactory {

    public static OrderService create(RiskManagementService riskManagementService){
        return create(riskManagementService, new MemoryOrderDao());
    }

    public static OrderService create(RiskManagementService riskManagementService, OrderDao orderDao){
        return new OrderService(orderDao, riskManagementService);
    }

    public static List<PropertyChangeListener> createListeners(
            PortfolioModel portfolio,
            OrderService orderService,
            RiskManagementService riskManagementService,
            BrokerService executionHandler,
            EventNotifier eventNotifier
    ) {
        var listeners = new ArrayList<PropertyChangeListener>();
        listeners.add(new PositionClosedListener(orderService));
        listeners.add(new OrderFoundListener(
                executionHandler,
                orderService,
                eventNotifier,
                riskManagementService
        ));
        listeners.add(new SignalCreatedListener(
                riskManagementService,
                orderService,
                eventNotifier
        ));
        return listeners;
    }
}
