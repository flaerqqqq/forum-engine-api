package com.example.user_service.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * A Logback filter that decides whether to accept or deny log events based on the logger name.
 * <p>
 * This filter is used to include or exclude log events from specific packages in the log output.
 * In this implementation, log events originating from the "com.example.user_service" package
 * are accepted, while others are denied.
 * </p>
 */
public class WebRequestFilter extends Filter<ILoggingEvent> {

    /**
     * Decides whether the given log event should be accepted or denied based on the logger name.
     * <p>
     * If the logger name of the event starts with "com.example.user_service", the event is accepted.
     * Otherwise, the event is denied.
     * </p>
     *
     * @param event the log event to be filtered
     * @return {@link FilterReply#ACCEPT} if the event should be logged, {@link FilterReply#DENY} otherwise
     */
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().startsWith("com.example.user_service")) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
