package com.example.user_service.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class WebRequestFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().startsWith("org.springframework.web")) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
