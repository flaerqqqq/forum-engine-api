package com.example.authservice.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Custom logging filter for filtering log events based on the logger name.
 * This filter only allows log events from loggers within the "com.example.authservice" package.
 * All other log events are denied.
 *
 * <p>
 * - If the logger name starts with "com.example.authservice", the log event is accepted.
 * - Otherwise, the log event is denied.
 * </p>
 *
 * This class extends {@link Filter} and implements the {@link Filter#decide(ILoggingEvent)} method
 * to control the acceptance or denial of log events.
 */
public class WebRequestFilter extends Filter<ILoggingEvent> {

    /**
     * Decides whether to accept or deny a log event based on the logger's name.
     * If the logger name starts with "com.example.authservice", the log event is accepted.
     * Otherwise, it is denied.
     *
     * @param iLoggingEvent the logging event to be evaluated
     * @return {@link FilterReply#ACCEPT} if the logger name starts with "com.example.authservice",
     *         otherwise {@link FilterReply#DENY}
     */
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent.getLoggerName().startsWith("com.example.authservice")) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
