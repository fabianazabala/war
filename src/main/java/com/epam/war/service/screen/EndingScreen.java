package com.epam.war.service.screen;

import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndingScreen extends Screen {

  private static final Logger logger = LoggerFactory.getLogger(EndingScreen.class);
  private final DeckSize deckSize;
  private final int initialPlayerNumber;

  public EndingScreen(DeckSize deckSize, int initialPlayerNumber) {
    this.deckSize = deckSize;
    this.initialPlayerNumber = initialPlayerNumber;
  }

  /**
   * Logs an ending screen using game winner and total number of turns played.
   *
   * @param winner game winner.
   * @param turn   total number of turns played.
   */
  public void printEndingScreen(Player winner, int turn) {
    logger.info(SEPARATOR + "\n" + "WAR, " + initialPlayerNumber + " players" + ", " +
        deckSize + " deck" + ", " +
        "turn #" + turn + " - " + winner.getName() + " WON with " +
        winner.getHand().size() + "/" + deckSize.getCardCount() + " cards");
  }

}
