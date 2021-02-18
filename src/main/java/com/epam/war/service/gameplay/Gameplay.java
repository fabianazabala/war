package com.epam.war.service.gameplay;

import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.WarScreen;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Gameplay {

  private final CardsFinder cardsFinder;
  private final WarScreen warScreen;
  private final GameScreen gameScreen;

  private int turn;

  public Gameplay(CardsFinder cardsFinder, GameScreen gameScreen, WarScreen warScreen) {
    this(cardsFinder, warScreen, gameScreen, 1);
  }

  public Gameplay(CardsFinder cardsFinder, WarScreen warScreen, GameScreen gameScreen,
                  int turn) {
    this.cardsFinder = cardsFinder;
    this.warScreen = warScreen;
    this.gameScreen = gameScreen;
    this.turn = turn;
  }

  public void playTurn(List<Player> players) {
    Map<Card, Player> table = players.stream()
        .collect(Collectors.toMap(Player::playCard,
            p -> p, (o, o2) -> o2, LinkedHashMap::new));

    gameScreen.showScreen(players, table, turn);

    List<Card> highestCards = cardsFinder.findCard(table.keySet());

    Card winnerCard = Optional.of(highestCards)
        .filter(c -> c.size() > 1)
        .map(c -> {
          War war = new War(cardsFinder, warScreen);
          warScreen.printHeader(players, turn);
          Card winner = war.fight(c, table, new LinkedHashMap<>());
          warScreen.endWar(war.warRounds);
          return winner;
        })
        .orElse(highestCards.get(0));

    Player winner = table.get(winnerCard);

    table.keySet().stream().sorted().forEach(winner::takeCard);
    turn++;
  }

  private static final class War {

    private final CardsFinder highestCardsFinder;
    private final WarScreen warScreen;
    private int warRounds = 0;

    public War(CardsFinder highestCardsFinder, WarScreen warScreen) {
      this.highestCardsFinder = highestCardsFinder;
      this.warScreen = warScreen;
    }

    Card fight(List<Card> highestCards,
               Map<Card, Player> table,
               Map<Card, Player> warCards) {
      warRounds++;
      Map<Card, Player> newTable = highestCards.stream().map(table::get)
          .filter(Player::hasCards)
          .collect(Collectors.toMap(Player::playCard, p -> p, (t, t2) -> t2, LinkedHashMap::new));
      table.putAll(newTable);
      warCards.putAll(newTable);

      List<Card> newHighestCards = highestCardsFinder.findCard(newTable.keySet());

      warScreen.printTurn(warCards, newHighestCards);

      if (newHighestCards.size() > 1) {
        return fight(newHighestCards, table, warCards);
      } else {
        return newHighestCards.get(0);
      }
    }
  }
}
