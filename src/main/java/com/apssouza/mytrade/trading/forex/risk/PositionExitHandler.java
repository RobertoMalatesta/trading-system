package com.apssouza.mytrade.trading.forex.risk;

import com.apssouza.mytrade.feed.price.PriceHandler;
import com.apssouza.mytrade.trading.forex.portfolio.Portfolio;

public class PositionExitHandler {
    private final Portfolio portfolio;
    private final PriceHandler priceHandler;

    public PositionExitHandler(Portfolio portfolio, PriceHandler priceHandler) {
        this.portfolio = portfolio;
        this.priceHandler = priceHandler;
    }
}
