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

  public void showScreen(List<Player> players,
                         Map<Card, Player> tableForTurn,
                         int turn) {
    /*
    WAR, a players, b deck, turn #c - playerd leads with e/DECK_SIZE cards
    Player1 played: 7
    Player2 played: 4
    Player3 ...
    SEPARATOR
    */

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
