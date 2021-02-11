package com.epam.war.service.gameplay;

import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.screen.Game;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Gameplay {

  private final Random random;
  private final CardsFinder cardsFinder;
  private final War war;
  private final Game game;

  private int turn;

  public Gameplay(Random random, CardsFinder cardsFinder, Game game) {
    this(random, cardsFinder, game, 1);
  }

  public Gameplay(Random random, CardsFinder cardsFinder, Game game, int turn) {
    this.random = random;
    this.cardsFinder = cardsFinder;
    this.game = game;
    this.turn = turn;
    this.war = new War(cardsFinder, random);
  }

  public void playTurn(List<Player> players) {
    Map<Card, Player> table = players.stream()
        .collect(Collectors.toMap(p -> p.takeRandomCardFromHand(random),
            p -> p));

    game.showScreen(players, table, turn);

    List<Card> highestCards = cardsFinder.findCard(table.keySet());

    Card winnerCard = Optional.of(highestCards)
        .filter(c -> c.size() > 1)
        .map(c -> war.fight(c, table))
        .orElse(highestCards.get(0));

    Player winner = table.get(winnerCard);

    table.keySet().forEach(winner::takeCard);
    turn++;
  }

  private static final class War {

    private final CardsFinder highestCardsFinder;
    private final Random random;

    public War(CardsFinder highestCardsFinder, Random random) {
      this.highestCardsFinder = highestCardsFinder;
      this.random = random;
    }

    Card fight(List<Card> highestCards, Map<Card, Player> table) {
      Map<Card, Player> newTable = highestCards.stream().map(table::get)
          .filter(Player::hasCards)
          .collect(Collectors.toMap(p -> p.takeRandomCardFromHand(random), p -> p));
      table.putAll(newTable);

      List<Card> newHighestCards = highestCardsFinder.findCard(newTable.keySet());

      if (newHighestCards.size() > 1) {
        return fight(newHighestCards, table);
      } else {
        return newHighestCards.get(0);
      }
    }
  }
}
