package com.epam.war.domain;

import java.util.Objects;
import java.util.UUID;

public class Card implements Comparable<Card> {

  private final UUID cardId;
  private final CardValue value;
  private final CardSuit suit;

  public Card(CardValue value, CardSuit suit) {
    this.cardId = UUID.randomUUID();
    this.value = value;
    this.suit = suit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(cardId, card.cardId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardId);
  }

  public CardValue getValue() {
    return value;
  }

  public CardSuit getSuit() {
    return suit;
  }

  @Override
  public int compareTo(Card o) {
    return Integer.compare(value.getValue(), o.value.getValue());
  }
}

