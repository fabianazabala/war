package com.epam.war.service.application;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.support.LoggerTest;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InputWarApplicationTest extends LoggerTest {

  @BeforeClass
  public void beforeClass() {
    setUpLogger(LoggerFactory.getLogger(""));
  }

  @Test
  public void givenSmallDeck_thenInputWarApplicationIsPlayed() {
    InputWarApplication inputWarApplication = new InputWarApplication(
        new String[] {"4", "small"});
    inputWarApplication.start();

    List<String> loggedMessages = getFormattedMessages();
    assertThat(loggedMessages)
        .isNotEmpty();
    assertThat(loggedMessages.get(0))
        .startsWith("WAR, 4 players, SMALL deck, DEALING");
    assertThat(loggedMessages.get(loggedMessages.size() - 1))
        .startsWith("=========\n" +
            "WAR, 4 players, SMALL deck, turn")
        .contains("WON with 24/24 cards");
  }
}