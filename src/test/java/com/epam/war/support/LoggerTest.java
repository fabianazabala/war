package com.epam.war.support;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class LoggerTest {
  protected ListAppender listAppender;

  @BeforeClass
  public void setUpLogger() {
    LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);

    Configuration configuration = loggerContext.getConfiguration();
    LoggerConfig rootLoggerConfig = configuration.getLoggerConfig("");
    listAppender = new ListAppender("testAppender");
    listAppender.start();
    rootLoggerConfig.addAppender(listAppender, Level.ALL, null);
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
