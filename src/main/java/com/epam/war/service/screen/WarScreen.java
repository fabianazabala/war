package com.epam.war.service.screen;

import static java.util.function.Predicate.not;


import com.epam.war.domain.Card;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
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

  public void printTurn(List<Pair<Optional<Card>, Player>> table, Player winner) {
    Map<Player, List<Optional<Card>>> playedCards = new LinkedHashMap<>();
    table.forEach(pair -> {
      playedCards.putIfAbsent(pair.getRight(), new ArrayList<>());
      playedCards.get(pair.getRight()).add(pair.getLeft());
    });

    playedCards.forEach((player, cards) -> logger.info(playerMessage(winner, player, cards)));
  }

  private String playerMessage(Player winner, Player player, List<Optional<Card>> cards) {
    return player.getName() +
        " played: " +
        cards.stream()
            .map(card -> card.map(Card::toString).orElse(player.equals(winner) ? "" : "EoC"))
            .filter(not(String::isBlank))
            .collect(Collectors.joining(" ")) +
        ending(winner, player);
  }

  private String ending(Player winner, Player player) {
    return winner.equals(player) ? " -----------> WINNER FOUND!" : "";
  }

  public void endWar(int warRounds) {
    String message = "War lasted " + warRounds + " rounds.\n" + SEPARATOR;
    logger.info(message);
  }
}
