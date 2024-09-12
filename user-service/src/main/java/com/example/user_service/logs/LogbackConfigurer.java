package com.example.user_service.logs;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Configures Logback logging settings upon application startup.
 * <p>
 * This component listens for the {@link ApplicationReadyEvent} and sets up a custom log appender for Logback.
 * It adds the {@link LogAppender} to the root logger and configures it with necessary settings.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class LogbackConfigurer implements ApplicationListener<ApplicationReadyEvent> {

    private final LogAppender customAppender;

    /**
     * Handles the {@link ApplicationReadyEvent} and configures the Logback root logger.
     * <p>
     * This method sets the name of the custom appender, configures it with the Logback context,
     * adds a filter to the appender, starts the appender, and finally adds it to the root logger.
     * </p>
     *
     * @param event the application ready event
     */
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
