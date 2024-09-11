package com.example.authservice.logs;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogbackConfigurer implements ApplicationListener<ApplicationReadyEvent> {

    private final LogAppender logAppender;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logAppender.setName("LOG-SERVICE");
        logAppender.setContext(rootLogger.getLoggerContext());
        logAppender.addFilter(new WebRequestFilter());
        logAppender.start();
        rootLogger.addAppender(logAppender);
    }
}
