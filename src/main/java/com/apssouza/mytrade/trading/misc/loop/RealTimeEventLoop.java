package com.apssouza.mytrade.trading.misc.loop;

import com.apssouza.mytrade.trading.misc.helper.time.DateTimeHelper;
import com.apssouza.mytrade.trading.misc.helper.time.Interval;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public class RealTimeEventLoop extends AbstractTimeEventLoop {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final TemporalAmount frequency;
    private final CurrentTimeCreator current_time_creator;
    private LocalDateTime previous;
    private LocalDateTime current;

    public RealTimeEventLoop(
            LocalDateTime start,
            LocalDateTime end,
            TemporalAmount frequency ,
            CurrentTimeCreator current_time_creator
    ) {
        this.start = start;
        this.end = end;
        this.frequency = frequency;
        this.current_time_creator = current_time_creator;
    }

    @Override
    public boolean hasNext() {
        if (this.aborted)
            return false;
        LocalDateTime now = this.current_time_creator.getNow();

        if (DateTimeHelper.compare(this.end,">", now)  && DateTimeHelper.compare(this.end,">=", now))
            return true;
        return false;
    }

    @Override
    public void sleep() {
        LocalDateTime next = this.current.plus(this.frequency);
        LocalDateTime now = this.current_time_creator.getNow();
        LocalDateTime nextNoSec =  next.withSecond(0);
        if (DateTimeHelper.compare(now,"<", nextNoSec)){
            Interval interval = DateTimeHelper.calculate(next, nextNoSec);
            try {
                Thread.sleep(interval.getMilliseconds());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public LocalDateTime getNext() {
        this.previous = this.current;
        this.current = this.current_time_creator.getNow();
        return this.current;
    }
}
