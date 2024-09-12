package com.example.authservice.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class WebRequestFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        System.out.println(iLoggingEvent.getLoggerName());
        if (iLoggingEvent.getLoggerName().startsWith("com.example.authservice")) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
