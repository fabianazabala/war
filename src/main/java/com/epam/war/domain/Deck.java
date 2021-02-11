package com.epam.war.domain;

import java.util.List;
import java.util.Optional;

public class Deck {

  private final List<Card> cards;

  public Deck(List<Card> cards) {
    this.cards = cards;
  }

  public List<Card> getCards() {
    return cards;
  }

  public boolean hasCards() {
    return !cards.isEmpty();
  }

  public Optional<Card> pickCard() {
    if (!hasCards()) {
      return Optional.empty();
    }
    return Optional.of(cards.remove(cards.size() - 1));
  }
}

