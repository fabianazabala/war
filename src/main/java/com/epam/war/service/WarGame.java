package com.epam.war.service;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardDealer;
import com.epam.war.service.card.CardShuffler;
import com.epam.war.service.gameplay.Gameplay;
import java.util.List;
import java.util.function.Predicate;

public class WarGame {
  private final List<Player> players;
  private final Deck deck;
  private final CardShuffler shuffler;
  private final CardDealer dealer;
  private final Gameplay gameplay;

  public WarGame(List<Player> players,
                 Deck deck,
                 CardShuffler shuffler,
                 CardDealer dealer, Gameplay gameplay) {
    this.players = players;
    this.deck = deck;
    this.shuffler = shuffler;
    this.dealer = dealer;
    this.gameplay = gameplay;
  }

  public void play() {
    shuffler.shuffle(deck.getCards());
    dealer.deal(deck, players);

    while (players.size() > 1) {
      gameplay.playTurn(players);
      players.removeIf(handIsEmpty());
    }
  }

  private Predicate<Player> handIsEmpty() {
    return player -> player.getHand().isEmpty();
  }
}
