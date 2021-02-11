package com.epam.war.service.screen;

import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import com.epam.war.service.input.Input;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.List;
import java.util.Map;

public class Game extends Screen {

  private final HighestHandPlayersFinder highestHandPlayersFinder;
  private final Input input;

  public Game(HighestHandPlayersFinder highestHandPlayersFinder, Input input) {
    this.highestHandPlayersFinder = highestHandPlayersFinder;
    this.input = input;
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
    message.append("WAR, ").append(players.size()).append(", ")
        .append(input.getDeckSize()).append(" deck").append(", ")
        .append("turn #").append(turn).append(" - ")
        .append(getLeadMessage(players)).append("\n");
    tableForTurn.forEach((k, v) ->
        message.append(v.getName())
            .append(" played: ").append(k.getValue().getValue())
            .append("\n"));
    message.append(SEPARATOR);
    System.out.println(message.toString());
  }

  private String getLeadMessage(List<Player> players) {
    return highestHandPlayersFinder.findPlayers(players)
        .filter(p -> p.size() > 1)
        .map(p -> "DRAW with " + handCountString(p.get(0).getHand()))
        .orElse(players.get(0).getName() + " leads with " + handCountString(players.get(0).getHand()));
  }

  private String handCountString(List<Card> hand) {
    return hand.size() + "/" + input.getDeckSize().getCardCount() + " cards";
  }

}
