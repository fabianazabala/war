package com.epam.war.service.screen;

import static java.util.stream.Collectors.toList;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import com.epam.war.service.input.Input;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dealing extends Screen {

  private static final Logger logger = LoggerFactory.getLogger(Dealing.class);

  private final HighestHandPlayersFinder highestHandPlayersFinder;
  private final Input input;

  public Dealing(HighestHandPlayersFinder highestHandPlayersFinder, Input input) {
    this.highestHandPlayersFinder = highestHandPlayersFinder;
    this.input = input;
  }

  /**
   * Logs a dealing screen which shows that cards has been distributed amongst players.
   *
   * @param players list of players which received the cards.
   */
  public void showScreen(List<Player> players) {
    StringBuilder message = new StringBuilder()
        .append("WAR, ")
        .append(input.getPlayerNumber()).append(" players, ")
        .append(input.getDeckSize()).append(" deck, ")
        .append("DEALING\n");
    players.forEach(p -> message.append(p.getName())
        .append(" hand: ").append(toSortedHandValue(p)).append("\n"));
    message.append("\n");
    message.append("Lucky players: ").append(highestHandPlayersFinder.findPlayers(players)
        .map(this::toPlayerNameList)
        .orElse("none"))
        .append(".");
    message.append("\n").append(SEPARATOR);

    logger.info(message.toString());
  }

  private String toPlayerNameList(List<Player> players) {
    return players.stream()
        .map(Player::getName)
        .collect(Collectors.joining(", "));
  }

  private List<Integer> toSortedHandValue(Player p) {
    return p.getHand().stream()
        .map(Card::getValue)
        .map(CardValue::getValue)
        .sorted(Comparator.reverseOrder())
        .collect(toList());
  }
}
