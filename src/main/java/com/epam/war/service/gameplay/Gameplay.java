package com.epam.war.service.gameplay;

import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.WarScreen;
import java.util.ArrayList;
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

  public Gameplay(CardsFinder cardsFinder, WarScreen warScreen, GameScreen gameScreen) {
    this(cardsFinder, warScreen, gameScreen, 0);
  }

  public Gameplay(CardsFinder cardsFinder, WarScreen warScreen, GameScreen gameScreen,
                  int turn) {
    this.cardsFinder = cardsFinder;
    this.warScreen = warScreen;
    this.gameScreen = gameScreen;
    this.turn = turn;
  }

  /**
   * Plays a single turn, every player will place the first card from their hands into the table.
   * In case of a draw for first place, all involved players will go into a {@link War} until there's
   * a winner.
   *
   * @param players active players.
   */
  public void playTurn(List<Player> players) {
    turn++;
    Map<Card, Player> table = players.stream()
        .collect(Collectors.toMap(Player::playCard,
            p -> p, (o, o2) -> o2, LinkedHashMap::new));

    gameScreen.showScreen(players, table, turn);

    List<Card> highestCards = cardsFinder.findCard(table.keySet());

    Card winnerCard = Optional.of(highestCards)
        .filter(c -> c.size() > 1)
        .map(tiedCards -> {
          War war = new War(cardsFinder, new LinkedHashMap<>());
          Card winner = war.fight(tiedCards, table);
          warScreen.showScreen(players, turn, war.warCards, war.warHighestCards, war.warRounds);
          return winner;
        })
        .orElse(highestCards.get(0));

    Player winner = table.get(winnerCard);

    table.keySet().stream().sorted().forEach(winner::takeCard);
  }

  public int getTurn() {
    return turn;
  }

  private static final class War {

    private final CardsFinder highestCardsFinder;
    private final Map<Card, Player> warCards;
    private List<Card> warHighestCards;

    private int warRounds = 0;

    private War(CardsFinder highestCardsFinder,
                Map<Card, Player> warCards) {
      this.highestCardsFinder = highestCardsFinder;
      this.warCards = warCards;
    }

    /**
     * Players will go into a war playing their first card, whoever has the highest card will win the war.
     * In case of a draw then players will continue the war until there's a winner.
     *
     * @param highestCards tied cards which started this war.
     * @param table        K,V which contains cards in the table and players who played them.
     * @return winner card.
     */
    private Card fight(List<Card> highestCards,
                       Map<Card, Player> table) {
      warRounds++;
      LinkedHashMap<Card, Player> newTable = highestCards.stream().map(table::get)
          .filter(Player::hasCards)
          .collect(Collectors.toMap(Player::playCard, p -> p, (t, t2) -> t2, LinkedHashMap::new));
      table.putAll(newTable);
      warCards.putAll(newTable);

      List<Card> newHighestCards = highestCardsFinder.findCard(newTable.entrySet()
          .stream()
          .filter(entry -> entry.getValue().hasCards())
          .map(Map.Entry::getKey)
          .collect(Collectors.toList()));

      //If everybody ran out of cards then last player is the winner... That's unfair
      if (newHighestCards.isEmpty()) {
        newHighestCards = new ArrayList<>(warCards.keySet())
            .subList(warCards.size() - 1, warCards.size());
      }
      this.warHighestCards = newHighestCards;

      if (newHighestCards.size() > 1) {
        return fight(newHighestCards, table);
      } else {
        return newHighestCards.get(0);
      }
    }
  }
}
