package com.epam.war.domain;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {

  private final String name;
  private final List<Card> hand;

  public Player(String name,
                List<Card> hand) {
    this.name = name;
    this.hand = hand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
    Player player = (Player) o;
    return Objects.equals(name, player.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }

  public String getName() {
    return name;
  }

  public List<Card> getHand() {
    return hand;
  }

  public Card takeRandomCardFromHand(Random random) {
    return hand.remove(random.nextInt(hand.size()));
  }

  public void takeCard(Card card) {
    hand.add(card);
  }

  @Override
  public String toString() {
    return name;
  }
}
