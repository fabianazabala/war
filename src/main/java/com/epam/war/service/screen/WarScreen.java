package com.epam.war.service.screen;

import com.epam.war.domain.Card;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarScreen extends GameplayScreen {

  private static final Logger logger = LoggerFactory.getLogger(WarScreen.class);

  public WarScreen(HighestHandPlayersFinder highestHandPlayersFinder,
                   DeckSize deckSize) {
    super(highestHandPlayersFinder, deckSize);
  }

  /*
    WAR, a players, b deck, turn #c - playerd leads with e/DECK_SIZE cards
    ================================================================
                     WE HAVE A WAR LADIES AND GENTLEMEN!
    ================================================================
    Player1 played: 7 ? 9
    Player2 played: 7 ? 11 -----------> WINNER FOUND!

    War lasted 2 rounds.
    SEPARATOR
   */

  public void printHeader(List<Player> players, int turn) {
    String message = super.warHeaderMessage(players, turn) +
        "================================================================\n" +
        "                 WE HAVE A WAR LADIES AND GENTLEMEN!\n" +
        "================================================================";
    logger.info(message);
  }

  public void printTurn(Map<Card, Player> table, List<Card> highestCards) {
    Optional<Player> winner = Stream.of(highestCards)
        .filter(cards -> cards.size() == 1)
        .findFirst()
        .map(cards -> table.get(cards.get(0)));

    Map<Player, List<Card>> playedCards = new HashMap<>();
    table.forEach((card, player) -> {
      playedCards.putIfAbsent(player, new ArrayList<>());
      playedCards.get(player).add(card);
    });

    playedCards.forEach((player, cards) -> logger.info(playerMessage(winner, player, cards)));
  }

  private String playerMessage(Optional<Player> winner, Player player, List<Card> cards) {
    return player.getName() +
        " played: " +
        cards.stream()
            .map(Card::getValue)
            .map(CardValue::getValue)
            .map(String::valueOf)
            .collect(Collectors.joining(" ? ")) +
        ending(winner, player);
  }

  private String ending(Optional<Player> winner, Player player) {
    return winner.filter(p -> p.equals(player))
        .map(p -> " -----------> WINNER FOUND!")
        .orElse(player.hasCards() ? "" : " ? EoC");
  }

  public void endWar(int warRounds) {
    String message = "War lasted " + warRounds + " rounds.\n" + SEPARATOR;
    logger.info(message);
  }
}
