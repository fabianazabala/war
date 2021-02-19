package com.epam.war.support;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;

public class LoggerTest {
  protected ListAppender listAppender;

  public void setUpLogger(Logger logger) {
    LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);

    Configuration configuration = loggerContext.getConfiguration();
    LoggerConfig loggerConfiguration = configuration.getLoggerConfig(logger.getName());
    listAppender = new ListAppender(logger.getName() + "_testAppender" + UUID.randomUUID());
    listAppender.start();
    loggerConfiguration.addAppender(listAppender, Level.ALL, null);
  }

  @BeforeMethod
  public void cleanAppender() {
    listAppender.clear();
  }

  protected List<String> getFormattedMessages() {
    return listAppender.getEvents().stream()
        .map(LogEvent::getMessage)
        .map(Message::getFormattedMessage)
        .collect(Collectors.toList());
  }
}
