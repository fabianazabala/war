package com.epam.war.service.screen;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.support.LoggerTest;
import com.epam.war.support.TestPlayerGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EndingScreenTest extends LoggerTest {

  private static final Logger logger = LoggerFactory.getLogger(EndingScreen.class);

  @BeforeClass
  public void beforeClass() {
    setUpLogger(logger);
  }

  @Test
  public void givenInputParametersAndWinner_thenGameScreenIsAsExpected() {
    EndingScreen endingScreen = new EndingScreen(DeckSize.SMALL, 4);
    Player winner = TestPlayerGenerator.withHighHand(1, 24);

    endingScreen.printEndingScreen(winner, 123);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("=========\n" +
            "WAR, 4 players, SMALL deck, turn #123 - player1 WON with 24/24 cards");
  }
}