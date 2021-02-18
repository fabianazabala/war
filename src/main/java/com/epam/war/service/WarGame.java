package com.epam.war.service;

import com.epam.war.domain.Player;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.screen.EndingScreen;
import java.util.List;
import java.util.function.Predicate;

public class WarGame {
  protected final List<Player> players;
  protected final Gameplay gameplay;
  protected final EndingScreen endingScreen;

  public WarGame(List<Player> players,
                 Gameplay gameplay,
                 EndingScreen endingScreen) {
    this.players = players;
    this.gameplay = gameplay;
    this.endingScreen = endingScreen;
  }

  /**
   * Executes {@link Gameplay#playTurn(List)} until there's a winner, then shows ending screen.
   */
  public void play() {
    while (players.size() > 1) {
      gameplay.playTurn(players);
      players.removeIf(Predicate.not(Player::hasCards));
    }
    Player winner = players.get(0);
    endingScreen.printEndingScreen(winner, gameplay.getTurn());
  }
}
