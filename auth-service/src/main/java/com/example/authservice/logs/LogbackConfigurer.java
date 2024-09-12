package com.example.authservice.logs;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Configures the custom {@link LogAppender} for the application upon startup.
 * This class listens for the {@link ApplicationReadyEvent}, which is triggered when the application is fully ready to service requests.
 * It adds the custom log appender to the root logger to send logs to an external service.
 * <p>
 * - The appender is named "LOG-SERVICE".
 * - It includes a custom filter {@link WebRequestFilter} for filtering log events.
 * </p>
 *
 * Annotations:
 * - {@code @Component}: Marks this class as a Spring component.
 * - {@code @RequiredArgsConstructor}: Automatically generates a constructor for the final {@link LogAppender} dependency.
 */
@Component
@RequiredArgsConstructor
public class LogbackConfigurer implements ApplicationListener<ApplicationReadyEvent> {

    private final LogAppender logAppender;

    /**
     * Configures the root logger to use the custom {@link LogAppender} when the application is ready.
     * This method is triggered by the {@link ApplicationReadyEvent}, which signals that the Spring Boot application has started and is ready to handle requests.
     * The appender is configured with the root logger's context, a filter is added, and the appender is started and attached to the root logger.
     *
     * @param event the event that indicates the application is ready
     */
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
