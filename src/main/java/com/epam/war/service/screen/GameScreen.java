package com.epam.war.service.screen;

import com.epam.war.domain.Card;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScreen extends GameplayScreen {

  private static final Logger logger = LoggerFactory.getLogger(GameScreen.class);

  public GameScreen(HighestHandPlayersFinder highestHandPlayersFinder, DeckSize deckSize) {
    super(highestHandPlayersFinder, deckSize);
  }

  /**
   * Logs a game screen which shows cards played in the current turn, list of turns and current lead.
   *
   * @param players      who played on this turn.
   * @param tableForTurn cards played on this turn.
   * @param turn         current turn number.
   */
  public void showScreen(List<Player> players,
                         Map<Card, Player> tableForTurn,
                         int turn) {
    StringBuilder message = new StringBuilder();
    message.append(warHeaderMessage(players, turn));
    tableForTurn.forEach((k, v) ->
        message.append(v.getName())
            .append(" played: ").append(k.getValue().getValue())
            .append("\n"));
    message.append(SEPARATOR);

    logger.info(message.toString());
  }
}
