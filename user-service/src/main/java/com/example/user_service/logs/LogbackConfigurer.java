package com.example.user_service.logs;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogbackConfigurer implements ApplicationListener<ApplicationReadyEvent> {

    private final LogAppender customAppender;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        customAppender.setName("LOG-SERVICE");
        customAppender.setContext(rootLogger.getLoggerContext());
        customAppender.addFilter(new WebRequestFilter());
        customAppender.start();
        rootLogger.addAppender(customAppender);
    }
}
