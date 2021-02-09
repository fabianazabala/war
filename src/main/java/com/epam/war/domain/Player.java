package com.epam.war.domain;

import java.util.List;

public class Player {

  private final String name;
  private final List<Card> hand;

  public Player(String name,
                List<Card> hand) {
    this.name = name;
    this.hand = hand;
  }

  public String getName() {
    return name;
  }

  public List<Card> getHand() {
    return hand;
  }

  public void takeCard(Card card) {
    hand.add(card);
  }

  @Override
  public String toString() {
    return name;
  }
}
