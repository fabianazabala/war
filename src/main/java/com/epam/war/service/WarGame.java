package com.epam.war.service;

import com.epam.war.domain.Player;
import com.epam.war.service.gameplay.Gameplay;
import java.util.List;
import java.util.function.Predicate;

public class WarGame {
  protected final List<Player> players;
  protected final Gameplay gameplay;

  public WarGame(List<Player> players,
                 Gameplay gameplay) {
    this.players = players;
    this.gameplay = gameplay;
  }

  public void play() {
    while (players.size() > 1) {
      gameplay.playTurn(players);
      players.removeIf(handIsEmpty());
    }
  }

  private Predicate<Player> handIsEmpty() {
    return player -> player.getHand().isEmpty();
  }
}
