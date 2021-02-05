package com.epam.war.domain;

import java.util.Objects;

public class Card {

  private final CardValue value;
  private final CardSuit suit;

  public Card(CardValue value, CardSuit suit) {
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
    return value == card.value && suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, suit);
  }

  public CardValue getValue() {
    return value;
  }

  public CardSuit getSuit() {
    return suit;
  }
}

