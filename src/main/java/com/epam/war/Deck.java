package com.epam.war;

import com.epam.war.card.Card;
import java.util.List;

public class Deck {

  private final List<Card> cards;

  public Deck(List<Card> cards) {
    this.cards = cards;
  }

  public List<Card> getCards() {
    return cards;
  }

  public Card pickCard(){
    return cards.remove(0);
  }
}

